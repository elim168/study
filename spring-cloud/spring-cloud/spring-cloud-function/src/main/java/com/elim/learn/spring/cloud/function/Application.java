package com.elim.learn.spring.cloud.function;

import com.alibaba.fastjson.JSON;
import com.elim.learn.spring.cloud.function.support.MessageSender;
import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Elim
 * 2019/1/28
 */
@SpringBootApplication
//@EnableBinding(CustomBinding.class)
public class Application {

  public static void main(String[] args) {
//    sendMessages();
//    receiveMessages();
    SpringApplication.run(Application.class, args);
  }

/*  @Bean
  public MessageSender messageSender(@Qualifier("output1") MessageChannel sender) {
    return new MessageSender(sender);
  }*/

  private static void sendMessages() {
    DefaultMQProducer producer = new DefaultMQProducer("test1");
    producer.setNamesrvAddr("localhost:9876");
    try {
      producer.start();
      for (int i=0; i<5; i++) {
        org.apache.rocketmq.common.message.Message message = new Message();
        message.setTopic("test-topic");
        message.setTags("tag1");
        message.setBody(("消息-" + i).getBytes());
        message.putUserProperty("stream_routekey", "consumer");
        producer.send(message);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      producer.shutdown();
    }
  }

  private static void receiveMessages() {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
    consumer.setNamesrvAddr("localhost:9876");
    try {
      consumer.subscribe("test-topic1", "*");
      consumer.setMessageListener(new MessageListenerConcurrently() {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
          msgs.forEach(msg -> {
            System.out.println("消费者收到消息：" + new String(msg.getBody()));
          });
          return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
      });
      consumer.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

/*  @Bean
  public Function<String, String> uppercase1() {
    return value -> value.toUpperCase();
  }

  @Bean
  public Function<Flux<String>, Flux<String>> uppercase() {
    return flux -> flux.map(value -> value.toUpperCase());
  }

  @Bean
  public Function<User, User> functionUser() {
    return input -> {
      input.setName("output:" + input.getName());
      return input;
    };
  }

  @Bean
  public Function<Message<String>, Message<String>> functionMessage() {
    return message -> {
      String payload = "response：" + message.getPayload();
      System.out.println("请求的Headers是：" + message.getHeaders());
      Map<String, Object> headers = new HashMap<>();
      headers.put("responseTime", LocalDateTime.now());
      MessageHeaders messageHeaders = new MessageHeaders(headers);
      return MessageBuilder.createMessage(payload, messageHeaders);
    };
  }*/

/*  @Bean
  public Consumer<org.springframework.messaging.Message<User>> consumer() {
    return message -> {
      System.out.println("收到消息：" + message);
      User user = message.getPayload();
      System.out.println(user.getName());
    };
  }*/

@Bean
public Consumer<org.springframework.messaging.Message<String>> consumer() {
  return message -> {
    System.out.println("Consumer收到消息：" + message.getHeaders());
  };
}

@Bean
public Function<String, String> function() {
  return input -> {
    System.out.println("Function接收到消息：" + input);
    return "response:" + input;
  };
}

/*  @Bean
  public Supplier<String> supplier() {
    return () -> "新消息" + LocalDateTime.now();
  }*/

/*@Bean
public Supplier<Flux<String>> supplier() {
  return () -> Flux.interval(Duration.ofSeconds(1)).map(i -> "新消息" + LocalDateTime.now());
}*/

/*  @Bean
  public Consumer<Flux<String>> consumerFlux() {
    return stringFlux -> {stringFlux.subscribe(str -> System.out.println("receive message : " + str));};
  }

  @Bean
  public Consumer<Flux<User>> consumerUser() {
    return flux -> flux.subscribe(user -> System.out.println("收到User消息：" + user));
  }


  *//**
   * Http请求的头信息会放到Message的头信息中
   * @return
   *//*
  @Bean
  public Consumer<Flux<Message<User>>> consumerMessage() {
    return flux -> flux.subscribe(message -> System.out.println("收到User消息：" + message.getPayload() + "，消息头是：" + message.getHeaders()));
  }*/

/*  @Bean
  public Supplier<String> supplier() {
    return () -> LocalDateTime.now().toString();
  }

  @Bean
  public Supplier<Flux<String>> supplierFlux() {
    return () -> Flux.interval(Duration.ofSeconds(1)).map(String::valueOf).take(5);
  }

  @Bean
  public Supplier<User> supplierUser() {
    return () -> {
      User user = new User();
      user.setId(System.currentTimeMillis());
      user.setName("User-" + System.nanoTime());
      return user;
    };
  }

  */
  @Data
  public static class User {
    private Long id;
    private String name;
  }
}
