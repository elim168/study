package com.elim.learn.spring.cloud.client.config;

import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.Span;

/**
 * @author Elim
 * 2019/5/6
 */
@Configuration
public class TracingConfiguration {

  @Bean
  public SpanAdjuster spanAdjuster() {
    return span -> {
      Span result = span.toBuilder().name("app-" + span.name()).addAnnotation(System.currentTimeMillis() * 1000, "reporting").build();
      return result;
    };
  }

}
