package com.elim.springboot.spring.core;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Component
public class MyWebClientCustomizer implements WebClientCustomizer {

    @Override
    public void customize(Builder webClientBuilder) {
        webClientBuilder.defaultCookie("cookieName", "cookieValue").defaultHeader("headerName", "headerValue");
    }

}
