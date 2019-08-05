package com.elim.learn.spring.cloud.stream.kafka.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.elim.learn.spring.cloud.stream.kafka.CustomBinding;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SourceProducer {

/*    @Autowired
    private Source source;*/
    @Autowired
    @Qualifier(CustomBinding.OUTPUT1)
    private MessageChannel messageChannel;
    
    @Autowired
    private CustomBinding customBinding;
    
/*    public void sendMessages(String msg) {
        String payload = msg;
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "testTag");
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<String> message = MessageBuilder.createMessage(payload, messageHeaders);
        this.source.output().send(message);
    }*/
    
    public void sendMessages(String msg) {
        Map<String, Object> headers = new HashMap<>();
        String tagName = "tag" + new Random().nextInt(5);
        headers.put(MessageConst.PROPERTY_TAGS, tagName);
        String payload = msg + "with tag[" + tagName + "]";
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<String> message = MessageBuilder.createMessage(payload, messageHeaders);
        log.info("发送了一条消息-{}", payload);
        this.messageChannel.send(message);
    }
    
    public void sendToOutput2(String msg) {
        String payload = msg;
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "testTag");
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<String> message = MessageBuilder.createMessage(payload, messageHeaders);
        this.customBinding.output2().send(message);
    }
    
}
