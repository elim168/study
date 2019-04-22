package com.elim.learn.spring.cloud.config.server.test;

import com.elim.learn.spring.cloud.config.server.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Elim
 * 2019/1/28
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class DiscoveryClientTest {

  private final String serviceId = "spring-cloud-consul-server";

  @Autowired
  private DiscoveryClient discoveryClient;

  @Test
  public void test() {
    List<String> serviceIds = this.discoveryClient.getServices();
    Assert.assertTrue(serviceIds.contains(serviceId));

    List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceId);
    System.out.println(instances);
    Assert.assertEquals(9100, instances.get(0).getPort());


  }

}
