package com.elim.learn.spring.cloud.stream.kafka.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


public interface Outputs {

    String OUTPUT3 = "output3";
    
    @Output
    MessageChannel output2();
    
    @Output
    MessageChannel userOutput();
    
    @Output(OUTPUT3)
    MessageChannel output3();
    
    @Output("output13")
    MessageChannel output31();
    
}
