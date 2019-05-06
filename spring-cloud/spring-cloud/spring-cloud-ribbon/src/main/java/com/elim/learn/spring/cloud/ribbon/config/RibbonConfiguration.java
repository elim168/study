package com.elim.learn.spring.cloud.ribbon.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule rule() {
        return new RoundRobinRule();
    }
    
    @Bean
    @LoadBalanced
    @Primary
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RestTemplate commonRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
