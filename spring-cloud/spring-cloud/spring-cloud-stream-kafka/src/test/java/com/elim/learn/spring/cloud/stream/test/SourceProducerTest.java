package com.elim.learn.spring.cloud.stream.test;

import com.elim.learn.spring.cloud.stream.kafka.Application;
import com.elim.learn.spring.cloud.stream.kafka.producer.SourceProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class SourceProducerTest {

    @Autowired
    private SourceProducer producer;

    @Test
    public void test() throws Exception {
        for (int i=0; i<10; i++) {
            this.producer.sendMessages(LocalDateTime.now().toString() + "Message-" + i);
        }
        TimeUnit.SECONDS.sleep(10);
    }
    
}
