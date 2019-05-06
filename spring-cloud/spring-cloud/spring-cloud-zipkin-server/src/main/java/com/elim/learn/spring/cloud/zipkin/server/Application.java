package com.elim.learn.spring.cloud.zipkin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.ZipkinServer;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * @author Elim
 * 2019/4/28
 */
@SpringBootApplication
@EnableZipkinServer
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
