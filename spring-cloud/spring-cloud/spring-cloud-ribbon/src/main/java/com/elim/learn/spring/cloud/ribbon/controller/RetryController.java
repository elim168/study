package com.elim.learn.spring.cloud.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RetryController {

    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("hello")
    public String hello() {
        String result = this.restTemplate.getForObject("http://hello/api/hello/abc", String.class);
        return result;
    }
    
    @GetMapping("retry")
    public String retry() {
        String result = this.restTemplate.getForObject("http://hello/api/hello/retryable", String.class);
        return result;
    }
    
    @GetMapping("retry/{sub}")
    public String retryAny(@PathVariable("sub") String sub) {
        String result = this.restTemplate.getForObject("http://hello/{sub}", String.class, sub);
        return result;
    }
    
}
