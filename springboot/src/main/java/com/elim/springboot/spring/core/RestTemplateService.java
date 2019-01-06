package com.elim.springboot.spring.core;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateService {

    private final RestTemplate restTemplate;
    
    public RestTemplateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    public String getSomething() {
        String content = this.restTemplate.getForObject("https://www.baidu.com", String.class);
        return content;
    }
    
}
