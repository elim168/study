package com.elim.study.sentinel.annotation;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Elim
 * 19-11-27
 */
@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class AppConfig {

  @Bean
  public SentinelResourceAspect sentinelResourceAspect() {
    return new SentinelResourceAspect();
  }

}
