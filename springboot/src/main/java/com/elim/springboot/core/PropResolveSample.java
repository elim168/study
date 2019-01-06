package com.elim.springboot.core;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="test.prop")
public class PropResolveSample {

    @Autowired
    private Environment env;
    
    @Value("${test.prop.a}")
    private String propA;
    
    @Value("${test.prop.b}")
    private String propB;
    
    @Value("${test.prop.c}")
    private String propC;
    
    private List<String> list;
    
    @PostConstruct
    public void init() {
        System.out.println(this.env.getProperty("elim.name"));
        System.out.println(this.env.getProperty("elim.name.default"));
        System.out.println(this.env.getProperty("elim.name.override"));
    }
    
}
