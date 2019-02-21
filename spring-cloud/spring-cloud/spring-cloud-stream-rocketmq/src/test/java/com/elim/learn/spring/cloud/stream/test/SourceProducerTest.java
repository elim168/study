package com.elim.learn.spring.cloud.stream.test;

import com.elim.learn.spring.cloud.stream.rocketmq.Application;
import com.elim.learn.spring.cloud.stream.rocketmq.model.User;
import com.elim.learn.spring.cloud.stream.rocketmq.producer.SourceProducer;
import com.elim.learn.spring.cloud.stream.rocketmq.producer.UserProducer;
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
    @Autowired
    private UserProducer userProducer;
    
    @Test
    public void test() throws Exception {
        for (int i=0; i<10; i++) {
            this.producer.sendMessages(LocalDateTime.now().toString() + "Message-" + i);
        }
        TimeUnit.SECONDS.sleep(10);
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
