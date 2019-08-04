package com.elim.study.kafka.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author Elim
 * 19-8-4
 */
public class ProducerTest {

  @Test
  public void test() throws Exception {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("acks", "all");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    String topic = "topic1";
    Producer<String, String> producer = new KafkaProducer<>(props);
    for (int i = 0; i < 10; i++) {
      Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(topic, "Key-" + i, "Value-" + i));
      RecordMetadata recordMetadata = future.get();
      System.out.println(recordMetadata.serializedKeySize() + "--" + recordMetadata.serializedValueSize() + "--" + recordMetadata.offset());
    }

    producer.close();
  }

}
