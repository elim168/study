package com.elim.learn.spring.cloud.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("api")
@Slf4j
public class ApiController {

    @GetMapping("hello/abc")
    public String hello(@RequestHeader Map<String, String> headers) {
        log.info("received................");
        return LocalDateTime.now().toString() + "-----" + headers;
    }
    
    private AtomicInteger retryableCounter = new AtomicInteger();
    
    @GetMapping("hello/retryable")
    public String retryable() {
        if (this.retryableCounter.incrementAndGet() % 3 != 0) {
            throw new IllegalStateException("Retry again." + this.retryableCounter.get());
        }
        return "retryable----" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
}
