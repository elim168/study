package com.elim.learn.spring.cloud.stream.kafka.producer;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import com.alibaba.fastjson.JSON;
import com.elim.learn.spring.cloud.stream.kafka.model.User;

public class UserMessageConverter extends AbstractMessageConverter {

    public UserMessageConverter() {
        super(MimeType.valueOf("application/json"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Object payload = message.getPayload();
        if (payload instanceof byte[]) {
            return JSON.parseObject((byte[])payload, targetClass);
        }
        return JSON.parseObject(payload.toString(), targetClass);
    }

    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        User user = (User) payload;
        user.setName("Converted by UserMessageConverter-------" + user.getName());
        return JSON.toJSONString(user);
    }

}
