package com.elim.study.kafka.test;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * @author Elim
 * 19-8-4
 */
public class ConsumerTest {

  @Test
  public void test() {
    Properties props = new Properties();
    props.setProperty("bootstrap.servers", "localhost:9092");
    props.setProperty("group.id", "group1");
    props.setProperty("enable.auto.commit", "true");
    props.setProperty("auto.commit.interval.ms", "1000");
    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

    Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);
    consumer.subscribe(Arrays.asList("topic1"));
    while (true) {
      ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
      consumerRecords.forEach(record -> {
        record.headers().forEach(header -> System.out.println("Header - " + header.key() + ": " + header.value()));
        System.out.println(new Date(record.timestamp()) + "---" + record.key() + " -- " + record.value());
      });
    }
  }

}
