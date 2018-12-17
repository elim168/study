package com.elim.learn.spring.cloud.zuul.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Bean
    public CloseableHttpClient httpClient() {
        List<Header> defaultHeaders = new ArrayList<>();
        defaultHeaders.add(new BasicHeader("abc", "123"));
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultHeaders(defaultHeaders).build();
        return httpClient;
    }
    
}
