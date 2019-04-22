package com.elim.learn.spring.cloud.function.support;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 2019/3/9
 */
@RequiredArgsConstructor
public class MessageSender {

  private final MessageChannel sender;

  @PostConstruct
  public void send() {
    new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Map<String, Object> headers = new HashMap<>();
      headers.put("stream_routekey", "consumer");
      MessageHeaders messageHeaders = new MessageHeaders(headers);
      for (int i=0; i<5; i++) {
        Message<String> message = MessageBuilder.createMessage("消息" + i, messageHeaders);
        this.sender.send(message);
      }
    }).start();
  }

}
