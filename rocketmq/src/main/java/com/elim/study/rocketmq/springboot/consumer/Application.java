package com.elim.study.rocketmq.springboot.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Elim
 * 19-7-31
 */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

@RocketMQMessageListener(topic = "topic1", consumerGroup = "group1")
@Component
private class MyRocketMQListener implements RocketMQListener<String> {

  @Override
  public void onMessage(String message) {
    System.out.println(LocalDateTime.now() + "接收到消息：" + message);
  }
}

  @RocketMQMessageListener(topic = "topic1", consumerGroup = "group1", selectorExpression = "tag1")
  @Component
  private class BaseTagRocketMQListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
      System.out.println(LocalDateTime.now() + "根据tag1接收到消息：" + message);
    }
  }

}
