package com.elim.learn.spring.cloud.stream.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.elim.learn.spring.cloud.stream.rocketmq.CustomBinding;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SinkConsumer {

    @StreamListener(Sink.INPUT)
    public void inputConsumer(Message<String> message) {
        String payload = message.getPayload();
        MessageHeaders headers = message.getHeaders();
        log.info("从Binding-{}收到信息-{}， headers：{}", Sink.INPUT, payload, headers);
    }
    
/*    @StreamListener(CustomBinding.INPUT1)
    public void input1Consumer(String message) {
        log.info("从Binding-{}收到信息-{}", CustomBinding.INPUT1, message);
    }*/
    
    
/*    @StreamListener(Sink.INPUT)
    @Output(Source.OUTPUT)
    public String receiveAndSend(String message) {
        return "receiveAndSend:" + message;
    }*/
    
    @StreamListener(CustomBinding.INPUT2)
    public void receiveFromInput2(String message) {
        log.info("从{}收到消息{}", CustomBinding.INPUT2, message);
    }
    
}
