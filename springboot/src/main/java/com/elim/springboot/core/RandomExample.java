package com.elim.springboot.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class RandomExample {

    @Value("${test.random.int}")
    private int randomInt;
    
    @Value("${test.random.int10}")
    private int randomInt10;
    
    @Value("${test.random.int10-100}")
    private int randomInt10_100;
    
    @Value("${test.random.long}")
    private long randomLong;
    
    @Value("${test.random.long100}")
    private long randomLong100;
    
    @Value("${test.random.uuid}")
    private String randomUUID;
    
    @Value("${test.random.bytes}")
    private String bytes;
    
    @Value("${random.int(1000)}")
    private int randomInt1000;
    
    @Value("${random.bytessss}")
    private String bytes2;
    
}
