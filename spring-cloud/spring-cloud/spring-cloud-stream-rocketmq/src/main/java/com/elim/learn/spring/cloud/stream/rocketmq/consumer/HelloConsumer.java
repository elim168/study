package com.elim.learn.spring.cloud.stream.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelloConsumer {

    @StreamListener(Sink.INPUT)
    public void receive(String message) {
        log.info("收到信息：{}", message);
    }
    
}
