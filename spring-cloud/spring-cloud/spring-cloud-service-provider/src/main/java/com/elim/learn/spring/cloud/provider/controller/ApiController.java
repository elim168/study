package com.elim.learn.spring.cloud.provider.controller;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    @GetMapping("hello/abc")
    public String hello(@RequestHeader Map<String, String> headers) {
        return LocalDateTime.now().toString() + "-----" + headers;
    }
    
    private AtomicInteger retryableCounter = new AtomicInteger();
    
    @GetMapping("hello/retryable")
    public String retryable() {
        if (this.retryableCounter.incrementAndGet() % 3 != 0) {
            throw new IllegalStateException("Retry again." + this.retryableCounter.get());
        }
        return "retryable----" + LocalDateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }
    
}
