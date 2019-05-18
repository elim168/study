package com.elim.springboot.core.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.listener.RetryListenerSupport;

/**
 * @author Elim
 * 19-5-18
 */
@EnableRetry
@Configuration
@PropertySource("classpath:/application.properties")
public class RetryConfiguration {

  @Bean
  public HelloService helloService() {
    return new HelloService();
  }

  @Bean
  public RetryListener retryListener() {
    return new RetryListenerSupport() {
      @Override
      public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        super.onError(context, callback, throwable);
        System.out.println("发生异常：" + context.getRetryCount());
      }
    };
  }

}
