package com.elim.learn.spring.cloud.stream.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SinkConsumer {

    @StreamListener(Sink.INPUT)
    public void inputConsumer(Message<String> message) {
        String payload = message.getPayload();
        MessageHeaders headers = message.getHeaders();
        log.info("从Binding-{}收到信息-{}， headers：{}", Sink.INPUT, payload, headers);
    }
    
}
