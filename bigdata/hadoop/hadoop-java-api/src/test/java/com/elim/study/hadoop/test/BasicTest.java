package com.elim.study.hadoop.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BasicTest {

    private FileSystem fileSystem;
    private Path dir = new Path("/user/elim");

    @Before
    public void before() throws IOException {
        Configuration configuration = new Configuration(true);
        this.fileSystem = FileSystem.get(configuration);
    }

    @After
    public void after() throws IOException {
        this.fileSystem.close();
    }

    @Test
    public void testMkdirs() throws IOException {
        System.out.println(this.fileSystem + "======" + this.fileSystem.getClass());
        if (!this.fileSystem.exists(this.dir)) {
            boolean result = this.fileSystem.mkdirs(dir);
            Assert.assertTrue(result);
        }
    }

    @Test
    public void testCreateFile() throws IOException {
        //

        try (FSDataOutputStream outputStream = this.fileSystem.create(new Path(dir, "hdfs-site.xml"), true);
             InputStream inputStream = this.getClass().getResourceAsStream("/hdfs-site.xml");) {
            IOUtils.copyBytes(inputStream, outputStream, 1024);

        }

    }

    @Test
    public void testReadFile() throws IOException {
        FSDataInputStream inputStream = this.fileSystem.open(new Path(dir, "hdfs-site.xml"));
        List<String> lines = org.apache.commons.io.IOUtils.readLines(inputStream, "UTF-8");
        lines.forEach(System.out::println);
    }

    @Test
    public void testDelete() throws IOException {
        boolean result = this.fileSystem.delete(new Path(dir, "hdfs-site.xml"), true);
        Assert.assertTrue(result);
    }

    @Test
    public void testGetBlock() throws IOException {
        BlockLocation[] fileBlockLocations = this.fileSystem.getFileBlockLocations(new Path(dir, "hadoop-common-3.2.1.jar"), 0, Integer.MAX_VALUE);
        for (BlockLocation location : fileBlockLocations) {
            System.out.println(location);
        }
        /**
         * Output:
         * 
         * 0,1048576,hadoop-master
         * 1048576,1048576,hadoop-master
         * 2097152,1048576,hadoop-master
         * 3145728,991792,hadoop-master
         */
    }

}
