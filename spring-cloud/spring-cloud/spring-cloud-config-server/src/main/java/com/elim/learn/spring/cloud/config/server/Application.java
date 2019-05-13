package com.elim.learn.spring.cloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Elim
 * 2019/1/28
 */
@SpringBootApplication
@EnableConfigServer
public class Application {

  public static void main(String[] args) {
    List<String> argList = new ArrayList<>(Arrays.asList(args));
    argList.add("--spring.config.location=classpath:/");
    args = argList.toArray(new String[0]);

    SpringApplication.run(Application.class, args);
  }

}
