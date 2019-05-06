package com.elim.learn.spring.cloud.config.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Elim
 * 2019/4/24
 */
@Configuration
public class BootstrapConfiguration {

  @Bean
  public TestService testService() {
    return new TestService();
  }

}
