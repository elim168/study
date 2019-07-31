package com.elim.study.rocketmq.springboot.producer;

import lombok.Data;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 19-7-31
 */
@SpringBootApplication
public class Application {

  @Autowired
  private RocketMQTemplate rocketMQTemplate;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner runner() {
    return args -> {
      new Thread(() -> {
        while (true) {
          Message<String> message = MessageBuilder.withPayload("Hello Elim! " + LocalDateTime.now()).build();
          String topic = "topic1";
          this.rocketMQTemplate.send(topic, message);
          this.rocketMQTemplate.convertAndSend(topic, new Person(1, "Name-" + LocalDateTime.now()));
          String destination = "topic1:tag1";//消息发到topic1,tag为tag1。
          this.rocketMQTemplate.convertAndSend(destination, "Hello, send message with tag");
          try {
            TimeUnit.SECONDS.sleep(10);
          } catch (InterruptedException e) {
            e.printStackTrace();
            break;
          }
        }
      }).start();
    };
  }

  @Data
  private class Person {

    private final Integer id;

    private final String name;

  }

}
