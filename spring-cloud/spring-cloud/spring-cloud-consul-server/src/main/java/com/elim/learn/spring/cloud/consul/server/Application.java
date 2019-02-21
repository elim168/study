package com.elim.learn.spring.cloud.consul.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author Elim
 * 2019/1/28
 */
@SpringBootApplication
@EnableHystrix
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
