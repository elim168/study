package com.elim.learn.spring.cloud.gateway.support;

import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author Elim
 * 2019/2/25
 */
public class MyRateLimiter implements RateLimiter {
  @Override
  public Mono<Response> isAllowed(String routeId, String id) {
    return null;
  }

  @Override
  public Map getConfig() {
    return null;
  }

  @Override
  public Class getConfigClass() {
    return null;
  }

  @Override
  public Object newConfig() {
    return null;
  }
}
