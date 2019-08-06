package com.elim.learn.spring.cloud.stream.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SourceProducer {

    @Autowired
    private Source source;

    public void sendMessages(String msg) {
        Message<String> message = MessageBuilder.withPayload(msg).build();
        log.info("发送了一条消息-{}", msg);
        this.source.output().send(message);
    }

}
