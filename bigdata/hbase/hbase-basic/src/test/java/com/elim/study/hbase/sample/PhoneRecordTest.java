package com.elim.study.hbase.sample;

import com.google.common.collect.Lists;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 测试通话记录<br/>
 * 通话记录的rowkey存储一方号码加时间戳，列存储对方号码、通话时间、通话时长、通话类型（1：呼入，2：呼出）
 */
public class PhoneRecordTest extends AbstractTest {

    private Random random = new Random();
    private TableName tableName = TableName.valueOf("phone_record");

    @Test
    public void testCreateTable() throws IOException {
        Admin admin = this.connection.getAdmin();
        //如果表已经存在了需要先删除表，删除表需要先禁用表才能删除
        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        }
        //列族
        ColumnFamilyDescriptor cf1 = ColumnFamilyDescriptorBuilder.of("cf1");
        TableDescriptor desc = TableDescriptorBuilder.newBuilder(tableName).setColumnFamily(cf1).build();
        //创建表
        admin.createTable(desc);
    }

    /**
     * 批量插入一批记录
     * @throws IOException
     */
    @Test
    public void testInsert() throws IOException {
        Table table = this.connection.getTable(tableName);
        List<Put> putList = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            String phoneNum = this.getPhoneNum("158", i);
            for (int j = 0; j < 100; j++) {
                Date recordDate = this.getRecordDate();
                String rowKey = this.getRowKey(phoneNum, recordDate);
                Put put = new Put(rowKey.getBytes());
                String otherNum = this.getPhoneNum("188", j);
                //通话类型，1：呼入，2：呼出
                String type = String.valueOf(this.random.nextInt(2) + 1);
                //通话时长，最长3600秒
                String keepTime = String.valueOf(this.random.nextInt(3600) + 1);
                //通话发生的时间
                String recordTime = new SimpleDateFormat("yyyyMMddHHmmss").format(recordDate);
                put.addColumn("cf1".getBytes(), "otherNum".getBytes(), otherNum.getBytes());
                put.addColumn("cf1".getBytes(), "type".getBytes(), type.getBytes());
                put.addColumn("cf1".getBytes(), "keepTime".getBytes(), keepTime.getBytes());
                put.addColumn("cf1".getBytes(), "recordTime".getBytes(), recordTime.getBytes());
                putList.add(put);
            }
        }
        table.put(putList);
    }

    private String getRowKey(String phoneNum, Date recordDate) {
        return phoneNum + "_" + (Long.MAX_VALUE - recordDate.getTime());
    }

    /**
     * 获取某个电话号码的某个月的通话记录。<br/>
     * 本测试测试的是3月份的呼入记录
     */
    @Test
    public void getTargetPhoneMonthRecords() throws IOException, ParseException {
        Table table = this.connection.getTable(tableName);
        String phoneNum = "158992589459";
        Date startDate = new SimpleDateFormat("yyyyMMdd").parse("20200301");
        Date endDate = new SimpleDateFormat("yyyyMMdd").parse("20200401");
        // 时间越大的rowkey中包含的时间戳数值越小
        String startRowKey = this.getRowKey(phoneNum, endDate);
        // 结束的rowkey，时间早的rowkey中包含的时间戳数值大
        String endRowKey = this.getRowKey(phoneNum, startDate);
        Scan scan = new Scan();
        // 第二个参数表示是否包含开始rowkey的记录，默认不传递该参数的重载方法是true，这里我们不包含20200401的记录。
        scan.withStartRow(startRowKey.getBytes(), false);
        // 包含20200301这一天的记录
        scan.withStopRow(endRowKey.getBytes());

        // 增加过滤条件
        FilterList filterList = new FilterList();
        // 需要过滤的type的值
        String typeFilterValue = "1";
        filterList.addFilter(new SingleColumnValueFilter("cf1".getBytes(), "type".getBytes(), CompareOperator.EQUAL, typeFilterValue.getBytes()));
        scan.setFilter(filterList);

        ResultScanner scanner = table.getScanner(scan);
        System.out.println(scanner.getScanMetrics());
        System.out.println(startRowKey);
        System.out.println(endRowKey);
        //输出结果集
        this.printResultScanner(scanner);
    }

    private void printResultScanner(ResultScanner scanner) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        scanner.forEach(result -> {
//            System.out.println(result.listCells());
            String rowKey = new String(result.getRow());
            String otherNum = new String(result.getValue("cf1".getBytes(), "otherNum".getBytes()));
            String type = new String(result.getValue("cf1".getBytes(), "type".getBytes()));
            String keepTime = new String(result.getValue("cf1".getBytes(), "keepTime".getBytes()));
            String recordTime = new String(result.getValue("cf1".getBytes(), "recordTime".getBytes()));

            recordTime = LocalDateTime.from(dateTimeFormatter.parse(recordTime)).toString();
            System.out.println("rowKey=" + rowKey + ", otherNum=" + otherNum + ", type=" + type
                    + ", keepTime = " + keepTime + ", recordTime = " + recordTime);
        });
    }

    /**
     * 查询某个手机号的呼入类型的记录
     */
    @Test
    public void queryPhoneInRecord() throws IOException {
        String phoneNum = "158992589459";
        String type = "1";
        Table table = this.connection.getTable(tableName);
        Scan scan = new Scan();
        FilterList filterList = new FilterList();
        // 通过rowkey的前缀来进行过滤，rowkey的前缀都是某个手机号
        PrefixFilter phoneNumPrefixFilter = new PrefixFilter(phoneNum.getBytes());
        SingleColumnValueFilter typeFilter = new SingleColumnValueFilter("cf1".getBytes(), "type".getBytes(), CompareOperator.EQUAL, type.getBytes());
        filterList.addFilter(phoneNumPrefixFilter);
        filterList.addFilter(typeFilter);
        scan.setFilter(filterList);
        // 限制返回的记录数
        scan.setLimit(5);

        ResultScanner scanner = table.getScanner(scan);
        this.printResultScanner(scanner);
    }

    /**
     * 删除记录，只能通过rowKey进行删除
     * @throws IOException
     */
    public void testDelete() throws IOException {
        Table table = this.connection.getTable(tableName);
        String rowKey = null;
        Delete delete = new Delete(rowKey.getBytes());
        table.delete(delete);
    }

    private String getPhoneNum(String numPrefix, int i) {
        String prefix = numPrefix + i;
        int suffixNum = random.nextInt((int) Math.pow(10, 12 - prefix.length()));
        return prefix + String.format("%0" + (11 - prefix.length()) + "d", suffixNum);
    }

    private Date getRecordDate() {
        int year = 2020;
        int month = random.nextInt(12);
        int date = random.nextInt(month == 1 ? 28 : 30) + 1;
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date, hour, minute, second);
        return calendar.getTime();
    }

}
