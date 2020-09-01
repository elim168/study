package com.elim.study.hbase.sample.protobuf;

import com.elim.study.hbase.sample.AbstractTest;
import com.google.common.collect.Lists;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 测试通话记录——通过protobuf进行序列化存储<br/>
 * 使用protobuf需要定义一个proto文件，在该文件中定义message（对应的信息结构），然后在机器上安装protobuf编译器，安装后可以使用protoc命令把
 * proto文件编译为Java源文件。之后就可以利用生成的源文件中包含的Java类进行对象的序列化和反序列化。<br/>
 * 编译命令为“protoc --java_out=/home/elim/dev DailyPhone.proto”，也可以通过“protoc -h”查看帮助文档
 * 通话记录的rowkey存储一方号码加时间戳，列存储对方号码、通话时间、通话时长、通话类型（1：呼入，2：呼出）
 */
public class DailyRecordProtobufTest extends AbstractTest {

    private Random random = new Random();
    private TableName tableName = TableName.valueOf("phone_daily_record_protobuf");

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
            String rowKey = this.getRowKey(phoneNum, this.getRecordDate());
            DailyPhone.DailyRecord.Builder dailyBuilder = DailyPhone.DailyRecord.newBuilder();
            for (int j = 0; j < 100; j++) {
                Date recordDate = this.getRecordDate();
                String otherNum = this.getPhoneNum("188", j);
                //通话类型，1：呼入，2：呼出
                String type = String.valueOf(this.random.nextInt(2) + 1);
                //通话时长，最长3600秒
                String keepTime = String.valueOf(this.random.nextInt(3600) + 1);
                //通话发生的时间
                String recordTime = new SimpleDateFormat("yyyyMMddHHmmss").format(recordDate);

                DailyPhone.PhoneRecord phoneRecord = DailyPhone.PhoneRecord.newBuilder()
                        .setOtherNum(otherNum)
                        .setType(type)
                        .setKeepTime(keepTime)
                        .setRecordTime(recordTime)
                        .build();
                dailyBuilder.addPhoneRecord(phoneRecord);
            }
            Put put = new Put(rowKey.getBytes());
            put.addColumn("cf1".getBytes(), "daily".getBytes(), dailyBuilder.build().toByteArray());
            putList.add(put);
        }
        table.put(putList);
    }

    private String getRowKey(String phoneNum, Date recordDate) {
        return phoneNum + "_" + (Long.MAX_VALUE - recordDate.getTime());
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
