package com.elim.learn.spring.cloud.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("spring-cloud-service-provider")
public interface HelloService {

    @GetMapping("hello")
    String helloWorld();
    
}
