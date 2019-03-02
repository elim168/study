package com.elim.learn.spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;

/**
 * @author Elim
 * 2019/1/28
 */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  public RemoteAddressResolver remoteAddressResolver() {
    return XForwardedRemoteAddressResolver.trustAll();
  }

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(r -> r.path("/api/**")
            .filters(f ->
                f.addResponseHeader("X-TestHeader", "foobar"))
            .uri("http://localhost:8900")
        ).build();
  }

}
