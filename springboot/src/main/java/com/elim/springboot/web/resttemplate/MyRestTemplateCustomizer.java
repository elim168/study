package com.elim.springboot.web.resttemplate;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MyRestTemplateCustomizer implements RestTemplateCustomizer {

    @Override
    public void customize(RestTemplate restTemplate) {
        System.out.println("===========================================");
        System.out.println("====================对RestTemplate自定义=======================");
        System.out.println("===========================================");
    }

}
