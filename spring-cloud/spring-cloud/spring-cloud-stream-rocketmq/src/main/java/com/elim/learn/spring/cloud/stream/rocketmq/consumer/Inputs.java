package com.elim.learn.spring.cloud.stream.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Inputs {

    String INPUT1 = "input1";
    String INPUT2 = "input2";
    
    @Input(Inputs.INPUT1)
    SubscribableChannel input1();
    
    @Input(Inputs.INPUT2)
    SubscribableChannel input2();
    
}
