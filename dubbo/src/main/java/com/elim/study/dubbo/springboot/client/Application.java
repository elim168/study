package com.elim.study.dubbo.springboot.client;

import com.elim.study.dubbo.springboot.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Elim
 * 19-7-29
 */
@SpringBootApplication
public class Application {

  @Reference
  private HelloService helloService;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner runner() {
    return args -> {
      String result = this.helloService.sayHello("Elim");
      System.out.println(result);
    };
  }

}
