package com.elim.learn.spring.cloud.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Elim
 * 2019/4/25
 */
@RestController
public class WebClientController {

  @Autowired
  private WebClient.Builder webClientBuilder;

  @Autowired
  private LoadBalancerExchangeFilterFunction lbFunction;

  @GetMapping("webClient/lbFunction")
  public Mono<String> lbFunction() {
    return WebClient.builder().baseUrl("http://hello")
        .filter(lbFunction)
        .build()
        .get()
        .uri("/api/hello/abc")
        .retrieve()
        .bodyToMono(String.class);
  }

  @GetMapping("webClient/hello")
  public Mono<String> hello() {
    return this.webClientBuilder.build().get().uri("http://hello/hello").retrieve().bodyToMono(String.class);
  }

}
