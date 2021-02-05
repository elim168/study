package com.elim.study.kafka.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Future;

/**
 * @author Elim
 * 19-8-4
 */
public class SparkStreamingKafkaTest {

    private Producer<String, String> producer;
    private String topic = "topic1";

    @Before
    public void before() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "172.18.0.2:9092,172.18.0.3:9092,172.18.0.4:9092");
        configs.put("acks", "all");
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(configs);
        this.producer = producer;
    }

    @Test
    public void generate4Wordcount() throws Exception {

        List<String> lines = new ArrayList<>();
        lines.add("Hello World");
        lines.add("Hello Spark");
        lines.add("Hello Java");
        lines.add("Hello Scala");
        lines.add("Hello Kafka");
        lines.add("Hello Spark Streaming");
        lines.add("Hello Spark Streaming Kafka");
        lines.add("Java Scala");
        lines.add("Spark Streaming");
        lines.add("Spark Kafka");

        Random random = new Random();

        int seconds = 30;
        long start = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() - start <= seconds * 1000) {
            Future<RecordMetadata> result = this.producer.send(new ProducerRecord<>(this.topic, lines.get(random.nextInt(lines.size()))));
            System.out.println(result.get().offset());
            count++;
        }
        System.out.println("消息发送完毕，总计发送数量为：" + count);
    }

    @After
    public void after() throws Exception {
        this.producer.close();
    }

}
