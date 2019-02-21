package com.elim.learn.spring.cloud.consul.client.test;

import com.elim.learn.spring.cloud.consul.client.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author Elim
 * 2019/1/28
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class RestTemplateTest {

  private final String serviceId = "spring-cloud-consul-server";

  @Autowired
  private RestTemplate restTemplate;

  @Test
  public void test() {
    String url = "http://" + serviceId + "/hello";
    String result = this.restTemplate.getForObject(url, String.class);
    System.out.println(result);
    Assert.assertTrue(result.startsWith("hello world"));

    ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class);
    System.out.println(responseEntity);
    Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    Assert.assertTrue(responseEntity.getBody().startsWith("hello world"));
  }

}
