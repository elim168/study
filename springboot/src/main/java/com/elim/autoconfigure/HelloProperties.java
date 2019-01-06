package com.elim.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("autoconfigure.hello")
public class HelloProperties {

    private String name;
    
    private String message;
    
}
