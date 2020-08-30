package com.elim.study.hbase.sample;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.metrics.ScanMetrics;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AbstractTest {

    protected Configuration configuration;
    protected Connection connection;

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

}
