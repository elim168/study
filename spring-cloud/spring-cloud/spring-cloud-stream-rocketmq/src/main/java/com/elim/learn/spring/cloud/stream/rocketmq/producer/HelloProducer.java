package com.elim.learn.spring.cloud.stream.rocketmq.producer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.elim.learn.spring.cloud.stream.rocketmq.model.User;

//@Component
public class HelloProducer {

//    @Autowired
    private MessageChannel output;
    
    @Autowired
    private Outputs outputs;
    
    @Autowired
    private MessageSendService messageSendService;

    private static AtomicLong counter = new AtomicLong();
    
    private ExecutorService executor = Executors.newCachedThreadPool();
    
    public void sendMessage(String msg) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "testTag");
        Message<String> message = MessageBuilder.createMessage(msg, new MessageHeaders(headers));
        output.send(message);
    }

    @PostConstruct
    public void init() {
        this.executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println("Ready To Send.................");
            for (int i=0; i<10; i++) {
//                Map<String, Object> headers = new HashMap<>();
//                headers.put(MessageConst.PROPERTY_TAGS, "testTag");
//                this.outputs.output2().send(MessageBuilder.createMessage("Send To Topic 2", new MessageHeaders(headers)));
//                this.sendMessage("Message-----------" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//                this.messageSendService.sendMessage("Send To Topic 2 ********** " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                
                
                this.sendUser();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
    }
    
    private void sendUser() {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "testTag");
        User user = new User();
        user.setId(counter.incrementAndGet());
        user.setName("User----" + UUID.randomUUID().toString());
        Message<User> message = MessageBuilder.createMessage(user, new MessageHeaders(headers));
//        this.outputs.userOutput().send(message);
        this.outputs.output3().send(message);
        
        
        //
//        this.messageSendService.send(user, "user-topic2");
    }
    
}
