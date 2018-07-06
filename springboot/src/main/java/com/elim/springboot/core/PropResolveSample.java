package com.elim.springboot.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="test.prop")
public class PropResolveSample {

    @Value("${test.prop.a}")
    private String propA;
    
    @Value("${test.prop.b}")
    private String propB;
    
    @Value("${test.prop.c}")
    private String propC;
    
    private List<String> list;
    
}
