package com.elim.learn.spring.cloud.stream.kafka.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Inputs {

    String INPUT1 = "input1";
    String INPUT3 = "input3";
    
/*    @Input(Inputs.INPUT1)
    SubscribableChannel input1();
    
    @Input
    SubscribableChannel userInput();
    
    @Input
    SubscribableChannel userInput2();
    */
    @Input(INPUT3)
    SubscribableChannel userInput3();
    
}
