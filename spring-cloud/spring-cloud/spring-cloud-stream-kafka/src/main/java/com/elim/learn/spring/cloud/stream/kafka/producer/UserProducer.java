package com.elim.learn.spring.cloud.stream.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import com.elim.learn.spring.cloud.stream.kafka.CustomBinding;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.elim.learn.spring.cloud.stream.kafka.model.User;

@Component
public class UserProducer {

    @Autowired
    @Qualifier(CustomBinding.OUTPUT1)
    private MessageChannel messageChannel;
    
    public void sendUser(User user) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "userTag");
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<User> message = MessageBuilder.createMessage(user, messageHeaders);
        this.messageChannel.send(message);
    }
    
}
