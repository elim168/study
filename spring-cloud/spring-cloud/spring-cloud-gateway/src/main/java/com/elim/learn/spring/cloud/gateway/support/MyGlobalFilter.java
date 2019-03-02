package com.elim.learn.spring.cloud.gateway.support;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author Elim
 * 2019/2/27
 */
@Component
@Order(1)
public class MyGlobalFilter implements GlobalFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    URI uri = exchange.getRequest().getURI();
    System.out.println("接收到请求：" + uri);
    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
      System.out.println("请求处理结束：" + uri);
    }));
  }
}
