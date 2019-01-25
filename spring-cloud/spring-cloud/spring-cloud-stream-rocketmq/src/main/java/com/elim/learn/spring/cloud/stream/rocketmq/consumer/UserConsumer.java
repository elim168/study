package com.elim.learn.spring.cloud.stream.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import com.elim.learn.spring.cloud.stream.rocketmq.CustomBinding;
import com.elim.learn.spring.cloud.stream.rocketmq.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserConsumer {

/*    @StreamListener(CustomBinding.INPUT1)
    public void consumeUser(User user) {
        log.info("从Binding-{}收到User类型的消息-{}", CustomBinding.INPUT1, user);
        if (user.getId() % 3 == 0) {
            throw new IllegalStateException();
        }
    }*/
    
/*    @StreamListener(CustomBinding.INPUT1)
    public void consumeUser(Message<User> message) {
        log.info("从Binding-{}收到User类型的消息-{}", CustomBinding.INPUT1, message);
        User user = message.getPayload();
        if (user.getId() % 3 == 0) {
            throw new IllegalStateException();
        }
    }*/
    
    
/*    @StreamListener(CustomBinding.INPUT1)
    public void consumeUser(String user) {
        log.info("从Binding-{}收到User类型的消息-{}", CustomBinding.INPUT1, user);
    }*/
    
    
/*    @ServiceActivator(inputChannel = "test-topic1.test-group1.errors")
    public void handleConsumeUserError(ErrorMessage message) {
        log.info("收到处理失败的消息{}", message.getPayload());
    }*/
    
/*    @StreamListener("errorChannel")
    public void handleErrors(ErrorMessage message) {
        log.info("默认的消息失败处理器收到处理失败的消息: {}，headers：{}", message.getOriginalMessage(), message.getHeaders());
    }*/
    
}
