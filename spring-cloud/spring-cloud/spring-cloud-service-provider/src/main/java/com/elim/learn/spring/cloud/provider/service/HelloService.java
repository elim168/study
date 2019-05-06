package com.elim.learn.spring.cloud.provider.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="spring-cloud-sleuth-service1")
public interface HelloService {

    @GetMapping("hello")
    String helloWorld();
    
    @GetMapping("api/hello/abc")
    String headers();
    
}
