package com.elim.learn.spring.cloud.consul.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Elim
 * 2019/1/28
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

/*  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }*/

}
