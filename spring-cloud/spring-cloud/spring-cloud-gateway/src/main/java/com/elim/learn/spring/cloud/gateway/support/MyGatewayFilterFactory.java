package com.elim.learn.spring.cloud.gateway.support;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * @author Elim
 * 2019/3/2
 */
@Component
public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<MyGatewayFilterFactory.Config> {

  private static final String NAME_KEY = "name";

  public MyGatewayFilterFactory() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      URI uri = exchange.getRequest().getURI();
      System.out.println("请求" + uri + "以前的处理，config配置的name为：" + config.getName());
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        System.out.println("请求" + uri + "以后的处理，config配置的name为：" + config.getName());
      }));
    };
  }

  /**
   * 在配置该Filter时，采用简写形式（即FilterName=参数）时后面的参数对应Config类中参数的顺序。
   * @return
   */
  @Override
  public List<String> shortcutFieldOrder() {
    return Arrays.asList(NAME_KEY);
  }

  @Data
  public static class Config {
    private String name;
  }

}
