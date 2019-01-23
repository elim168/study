package com.elim.learn.spring.cloud.stream.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elim.learn.spring.cloud.stream.rocketmq.Application;
import com.elim.learn.spring.cloud.stream.rocketmq.model.User;
import com.elim.learn.spring.cloud.stream.rocketmq.producer.SourceProducer;
import com.elim.learn.spring.cloud.stream.rocketmq.producer.UserProducer;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@Slf4j
public class SourceProducerTest {

    @Autowired
    private SourceProducer producer;
    @Autowired
    private UserProducer userProducer;
    
    @Test
    public void test() {
        for (int i=0; i<10; i++) {
            this.producer.sendMessages("Message-" + i);
            log.info("发送了一条消息{}", "Message-" + i);
        }
    }
    
    @Test
    public void testSendUser() throws Exception {
        for (int i=0; i<5; i++) {
            User user = new User();
            user.setId(i + 1L);
            user.setName("User_" + user.getId());
            this.userProducer.sendUser(user);
            TimeUnit.SECONDS.sleep(1);
        }
        TimeUnit.SECONDS.sleep(25);
    }
    
    @Test
    public void testSendUserNoError() throws Exception {
        for (int i=0; i<50; i++) {
            User user = new User();
            user.setId(i + 1L);
            user.setName("User_" + user.getId());
            this.userProducer.sendUser(user);
        }
        TimeUnit.SECONDS.sleep(25);
    }
    
    @Test
    public void sendToOutput2() throws Exception {
        for (int i=0; i<5; i++) {
            this.producer.sendToOutput2("Message-" + (i+1));
            TimeUnit.SECONDS.sleep(1);
        }
    }
    
}
