package com.elim.learn.spring.cloud.stream.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CustomBinding {

    String INPUT1 = "input1";
    String OUTPUT1 = "output1";
    String INPUT2 = "input2";
    String OUTPUT2 = "output2";
    
    @Input
    SubscribableChannel input1();
    
    @Output
    MessageChannel output1();
    
    @Input
    SubscribableChannel input2();
    
    @Output
    MessageChannel output2();
    
}
