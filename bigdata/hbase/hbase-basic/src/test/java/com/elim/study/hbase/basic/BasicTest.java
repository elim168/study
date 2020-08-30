package com.elim.study.hbase.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.metrics.ScanMetrics;
import org.apache.hadoop.hbase.filter.ColumnValueFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.NavigableMap;

public class BasicTest {

    private Configuration configuration;
    private Connection connection;

    @Before
    public void before() throws IOException {
        //调用create方法时默认会读取CLASSPATH下的第一个hbase-site.xml文件。
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "hbase-node1,hbase-node2,hbase-node3");
        connection = ConnectionFactory.createConnection(configuration);
    }

    @After
    public void after() throws IOException {
        connection.close();
    }

    @Test
    public void testCreateTable() throws IOException {
        Admin admin = this.connection.getAdmin();
        //表名
        TableName tableName = TableName.valueOf("person");

        //如果表已经存在了需要先删除表，删除表需要先禁用表才能删除
        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        }
        //列族
        ColumnFamilyDescriptor cf1 = ColumnFamilyDescriptorBuilder.of("cf1");
        TableDescriptor desc = TableDescriptorBuilder.newBuilder(tableName).setColumnFamily(cf1).build();
        admin.createTable(desc);
    }


    @Test
    public void testInsertData() throws IOException {
        Table table = this.connection.getTable(TableName.valueOf("person"));
        String rowKey = "1596815807518";
        Put put = new Put(rowKey.getBytes());
        put.addColumn("cf1".getBytes(), "name".getBytes(), "张三".getBytes("UTF-8"));
        put.addColumn("cf1".getBytes(), "age".getBytes(), "30".getBytes("UTF-8"));
        table.put(put);
    }

    @Test
    public void testUpdate() throws IOException {
        Table table = this.connection.getTable(TableName.valueOf("person"));
        //rowkey相同时对应的就是修改
        String rowKey = "1596815807518";
        Put put = new Put(rowKey.getBytes());
        put.addColumn("cf1".getBytes(), "name".getBytes(), "张三".getBytes("UTF-8"));
        put.addColumn("cf1".getBytes(), "age".getBytes(), "39".getBytes("UTF-8"));
        put.addColumn("cf1".getBytes(), "sex".getBytes(), "".getBytes("UTF-8"));
        table.put(put);
    }

    @Test
    public void testGet() throws IOException {
        Table table = this.connection.getTable(TableName.valueOf("person"));
        String rowkey = "1596815807518";
        Get get = new Get(rowkey.getBytes());
        //通过get()方法默认会返回所有的列，可以通过addFamily()筛选出需要返回的列族。也可以通过addColumn筛选出需要返回的列。
//        get.addFamily("cf1".getBytes());
//        get.addColumn("cf1".getBytes(), "name".getBytes());
        Result result = table.get(get);
        //包含列
        Assert.assertTrue(result.containsColumn("cf1".getBytes(), "name".getBytes()));
        //包含非空列
        Assert.assertTrue(result.containsNonEmptyColumn("cf1".getBytes(), "name".getBytes()));
        Assert.assertTrue(result.containsColumn("cf1".getBytes(), "sex".getBytes()));
        //包含空列
        Assert.assertTrue(result.containsEmptyColumn("cf1".getBytes(), "sex".getBytes()));
        //获取某个列
        Cell nameCell = result.getColumnLatestCell("cf1".getBytes(), "name".getBytes());
        //获取列的值用CellUtil.cloneValue(cell)
        Assert.assertTrue(new String(CellUtil.cloneValue(nameCell)).equals("张三"));
        NavigableMap<byte[], byte[]> familyMap = result.getFamilyMap("cf1".getBytes());
//        familyMap
        NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = result.getMap();


        //遍历列开始
        while (result.advance()) {
            Cell cell = result.current();
            System.out.println(String.format("当前列%s的值为%s", new String(CellUtil.cloneFamily(cell)) + ":" + new String(CellUtil.cloneQualifier(cell)), new String(CellUtil.cloneValue(cell))));
        }

        //也可以通过下面的方法进行遍历
        CellScanner cellScanner = result.cellScanner();
        while (cellScanner.advance()) {
            System.out.println(cellScanner.current());
        }
        //遍历列结束
    }

    @Test
    public void testScan() throws IOException {
        Table table = this.connection.getTable(TableName.valueOf("person"));
        Scan scan = new Scan();
        //可以指定需要返回的列
//        scan.addColumn("cf1".getBytes(), "name".getBytes());
//        scan.addColumn("cf1".getBytes(), "age".getBytes());
        scan.addFamily("cf1".getBytes());

        //可以增加过滤条件
//        Filter filter = new SingleColumnValueFilter("cf1".getBytes(), "name".getBytes(), CompareOperator.EQUAL, "张三".getBytes("UTF-8"));

        // 要同时满足多个条件可以使用FilterList，默认需要满足所有的条件。
        FilterList filter = new FilterList();
        filter.addFilter(new SingleColumnValueFilter("cf1".getBytes(), "name".getBytes(), CompareOperator.EQUAL, "张三".getBytes("UTF-8")));
        filter.addFilter(new SingleColumnValueFilter("cf1".getBytes(), "age".getBytes(), CompareOperator.EQUAL, "39".getBytes()));


        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        ScanMetrics scanMetrics = scanner.getScanMetrics();
        System.out.println(scanMetrics);
        scanner.forEach(result -> {
            System.out.println(result.listCells());
            String rowKey = new String(result.getRow());
            String name = new String(result.getValue("cf1".getBytes(), "name".getBytes()));
            String age = new String(result.getValue("cf1".getBytes(), "age".getBytes()));
            System.out.println("rowKey=" + rowKey + ", name=" + name + ", age=" + age);
        });
    }

    /**
     *
     * 参考地址：http://hbase.apache.org/book.html#config_timeouts
     *
     * 7.6. Timeout settings
     *
     * HBase provides a wide variety of timeout settings to limit the execution time of various remote operations.
     *
     *     hbase.rpc.timeout
     *
     *     hbase.rpc.read.timeout
     *
     *     hbase.rpc.write.timeout
     *
     *     hbase.client.operation.timeout
     *
     *     hbase.client.meta.operation.timeout
     *
     *     hbase.client.scanner.timeout.period
     *
     * The hbase.rpc.timeout property limits how long a single RPC call can run before timing out. To fine tune read or write related RPC timeouts set hbase.rpc.read.timeout and hbase.rpc.write.timeout configuration properties. In the absence of these properties hbase.rpc.timeout will be used.
     *
     * A higher-level timeout is hbase.client.operation.timeout which is valid for each client call. When an RPC call fails for instance for a timeout due to hbase.rpc.timeout it will be retried until hbase.client.operation.timeout is reached. Client operation timeout for system tables can be fine tuned by setting hbase.client.meta.operation.timeout configuration value. When this is not set its value will use hbase.client.operation.timeout.
     *
     * Timeout for scan operations is controlled differently. Use hbase.client.scanner.timeout.period property to set this timeout.
     */
    void setTimeout() {

    }

}
