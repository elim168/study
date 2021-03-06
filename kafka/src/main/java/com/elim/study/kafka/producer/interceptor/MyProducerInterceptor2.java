package com.elim.study.kafka.producer.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author Elim
 * 19-8-14
 */
public class MyProducerInterceptor2 implements ProducerInterceptor<String, String> {
  @Override
  public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
    System.out.println("进行消息发送拦截2：" + record.topic() + "----" + record.key() + "----" + record.value());
    record.headers().add("header1", "header1-value2".getBytes());
    return record;
  }

  @Override
  public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
    if (exception == null) {
      System.out.println("收到响应，发送消息成功2：" + metadata);
    } else {
      System.out.println("收到响应，发送消息失败2：" + exception.getMessage());
    }
  }

  @Override
  public void close() {
    System.out.println("Producer关闭时调用2");
  }

  @Override
  public void configure(Map<String, ?> configs) {
    System.out.println("已经配置好的配置信息有2：" + configs);
  }
}
