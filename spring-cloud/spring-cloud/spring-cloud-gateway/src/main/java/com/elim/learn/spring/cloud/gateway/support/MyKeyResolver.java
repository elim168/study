package com.elim.learn.spring.cloud.gateway.support;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Elim
 * 2019/2/25
 */
public class MyKeyResolver implements KeyResolver {

  @Override
  public Mono<String> resolve(ServerWebExchange exchange) {
    return Mono.just("test");//所有请求都可以认为是同一个的。
  }

}
