package com.elim.springboot.core;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.jmx.annotation.JmxEndpoint;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="hello")
public class HelloEndpoint {

    @ReadOperation
    public String hello() {
        return "Hello World";
    }
    
}
