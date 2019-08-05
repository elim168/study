package com.elim.learn.spring.cloud.stream.kafka.producer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.cloud.stream.reactive.FluxSender;
import org.springframework.cloud.stream.reactive.StreamEmitter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

//@Service
public class MessageSendService {

    @Autowired
    private BinderAwareChannelResolver resolver;
    
    /**
     * 这样不会转发
     * @param msg
     * @return
     */
    @SendTo("output2")
    public String sendMessage(String msg) {
        return msg;
    }
    
    /**
     * 每5秒会发送一次消息
     * @return
     */
    @StreamEmitter
    @Output("output2")
    public Flux<String> sendMsg() {
        return Flux.interval(Duration.ofSeconds(5)).map(l -> "Send Message From MessageSendService.........." + LocalDateTime.now());
    }
    
    /**
     * 每5秒会发送一次消息。定义FluxSender参数，通过它发送消息
     * @return
     */
    @StreamEmitter
    @Output("output2")
    public void sendMsg2(FluxSender sender) {
        Flux<String> flux = Flux.interval(Duration.ofSeconds(5)).map(l -> "Send Message From MessageSendService222222222.........." + LocalDateTime.now());
        sender.send(flux);
    }
    
    /**
     * 每5秒会发送一次消息。定义FluxSender参数，通过它发送消息。将@Output注解放到方法参数上
     * @return
     */
    @StreamEmitter
    public void sendMsg3(@Output("output2")FluxSender sender) {
        Flux<String> flux = Flux.interval(Duration.ofSeconds(5)).map(l -> "Send Message From MessageSendService33333333.........." + LocalDateTime.now());
        sender.send(flux);
    }
    
    
    public void send(Object obj, String topic) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "testTag");
        Message<Object> message = MessageBuilder.createMessage(obj, new MessageHeaders(headers));
        //动态解析destination发送信息，这里动态的destination是对应具体Binder实现的destination，比如RocketMQ的topic。
        this.resolver.resolveDestination(topic).send(message);
    }
    
}
