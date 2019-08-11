package com.elim.study.kafka.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author Elim
 * 19-8-4
 */
public class ProducerTest {

  private Producer<String, String> producer;
  private String topic = "topic1";

  @Before
  public void before() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:19092");
    props.put("acks", "all");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    Producer<String, String> producer = new KafkaProducer<>(props);
    this.producer = producer;
  }

  @Test
  public void test() throws Exception {
    for (int i = 0; i < 10; i++) {
      long timestamp = System.currentTimeMillis() - 60 * 60 * 24 * 1000;
      Future<RecordMetadata> future = this.producer.send(new ProducerRecord<String, String>(this.topic, 0, timestamp, "Key-" + i, "Value-" + i));
      RecordMetadata recordMetadata = future.get();
      System.out.println(recordMetadata.serializedKeySize() + "--" + recordMetadata.serializedValueSize() + "--" + recordMetadata.offset());
    }

    this.producer.close();
  }

  @Test
  public void test2() throws Exception {
ProducerRecord<String, String> record = new ProducerRecord<>(this.topic, "Value-" + LocalDateTime.now());
this.producer.send(record, (recordMetadata, e) -> {
  if (e != null) {
    System.out.println("消息发送失败：" + e);
  } else {
    System.out.println("消息发送成功：" + recordMetadata);
  }
});
  }

  @After
  public void after () throws Exception {
    this.producer.close();
  }

}
