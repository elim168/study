package com.elim.learn.spring.cloud.stream.kafka.consumer;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelloConsumer {

/*    @StreamListener(Sink.INPUT)
    public void receive(String message) {
        log.info("收到信息：{}", message);
    }*/
    
/*    @StreamListener(Sink.INPUT)
    @SendTo("output2")
    public String receiveAndToOutput2(String message) {
        log.info("收到信息：{}", message);
        return "receive from input and send to output2 : " + message;
    }*/
    
    /**
     * 接收消息的Channel在参数上通过@Input指定
     * @param flux
     */
/*    @StreamListener(Sink.INPUT)
    public void reactiveReceive(Flux<String> flux) {
        flux.subscribe(message -> {
            log.info("通过reactive方式收到信息: {}", message);
        });
    }*/
    
    /**
     * 从input Channel接收消息，进行处理后发到output2 Channel
     * @param flux
     * @return
     */
/*    @StreamListener
    @SendTo(CustomBinding.OUTPUT2)
    public Flux<String> reactiveReceiveAndSend(@Input(Sink.INPUT) Flux<String> flux) {
        return flux.map(message -> "通过reactive方式接收并处理后转发的新消息：" + message);
    }*/
    
    /**
     * 发送的Channel通过方法参数指定，参数类型是FluxSender，且需要加上@Output
     * @param flux
     * @param sender
     */
/*    @StreamListener
    public void reactiveReceiveAndSend(@Input(Sink.INPUT) Flux<String> flux, @Output(CustomBinding.OUTPUT2) FluxSender sender) {
        sender.send(flux.map(message -> "通过reactive方式接收并处理后转发的新消息：" + message));
    }*/
    
/*    @StreamListener("userInput")
    public void receiveUser(User user) {
        log.info("收到User信息：{}", user);
    }
    
    @StreamListener("userInput2")
    public void receiveUserFromTopic2(User user) {
        log.info("从user-topic2收到User信息：{}", user);
    }
    
    
    @StreamListener(Inputs.INPUT1)
    public void receive2(String message) {
        if (System.currentTimeMillis() % 5 == 0) {
//            throw new IllegalStateException("处理消息异常");
        }
        log.info("消费者2收到信息：{}", message);
    }*/
    
/*    @StreamListener(Inputs.INPUT3)
    public void receive3(User user) {
        log.info("收到User消息，id={}，完整对象是{}", user.getId(), user);
    }*/
    
/*    @StreamListener(Sink.INPUT)
    public void receive(String message) {
        log.info("收到消息-{}", message);
    }
    
    *//**
     * 某个Channel的消息处理失败时这样接收不了
     *//*
    @StreamListener("test-topic2.test-group2.errors")
    public void handleReceive2Error(Message<?> message) {
        log.error("监控到receive2处理消息失败{}", message);
    }*/
    
/*    @ServiceActivator(inputChannel="test-topic2.test-group2.errors")
    public void handleReceive2Error(Message<?> message) {
        log.error("监控到receive2处理消息失败{}", message);
    }*/
    
    
    /*@StreamListener("errorChannel")
    public void handleAllErrors(Message<?> message) {
        log.error("默认的异常处理器监控到处理失败的消息：{}", message);
    }*/
    
}
