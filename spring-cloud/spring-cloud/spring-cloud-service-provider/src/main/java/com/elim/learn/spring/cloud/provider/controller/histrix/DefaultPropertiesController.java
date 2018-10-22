package com.elim.learn.spring.cloud.provider.controller.histrix;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping("hystrix/properties")
@DefaultProperties(defaultFallback = "defaultFallback", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
        @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "5") })
public class DefaultPropertiesController {

    @GetMapping("error")
    @HystrixCommand
    public String error() {
        throw new IllegalStateException();
    }
    
    //...
    
    public String defaultFallback() {
        return "result from default fallback.";
    }

}
