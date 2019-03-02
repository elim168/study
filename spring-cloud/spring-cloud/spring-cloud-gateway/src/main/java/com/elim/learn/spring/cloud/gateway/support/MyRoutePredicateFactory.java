package com.elim.learn.spring.cloud.gateway.support;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Elim
 * 2019/3/2
 */
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

  private static final String NAME_KEY = "name";

  public MyRoutePredicateFactory() {
    super(Config.class);
  }

  @Override
  public Predicate<ServerWebExchange> apply(Config config) {
    return exchange -> {
      ServerHttpRequest request = exchange.getRequest();
      if (HttpMethod.GET.equals(request.getMethod())
          && request.getQueryParams().containsKey(config.getName())) {
        return true;
      }
      return false;
    };
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return Arrays.asList(NAME_KEY);
  }

  @Data
  public static class Config {
    private String name;
  }

}
