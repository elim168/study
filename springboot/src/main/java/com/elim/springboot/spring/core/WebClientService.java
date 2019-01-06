package com.elim.springboot.spring.core;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService {

    
    private final WebClient webClient;
    
    public WebClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8081").build();
    }
    
    public String getJson() {
        return this.webClient.get().uri("hello/json").retrieve().bodyToMono(String.class).block();
    }
    
    
}
