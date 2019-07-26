package com.elim.study.dubbo.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Elim
 * 19-7-26
 */
public class CuratorTest {

  @Test
  public void test() throws Exception {
    CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString("localhost:2181").retryPolicy(new RetryOneTime(1000)).build();
    curatorFramework.start();
    byte[] bytes = Files.readAllBytes(Paths.get("/home/elim/dev/tools/apache-products/zookeeper-3.4.10/dubbo.properties"));
    String path = "/dubbo/config/dubbo/dubbo.properties";
    Stat stat = curatorFramework.setData().forPath(path, bytes);
    System.out.println(stat.getDataLength());


    byte[] nodeBytes = curatorFramework.getData().forPath(path).clone();
    System.out.println(new String(nodeBytes));
  }

}
