package com.elim.springboot.core.retry;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elim
 * 19-5-18
 */
@Retryable
public class HelloService {

  @Retryable(maxAttemptsExpression = "${retry.maxAttempts:5}",
          backoff = @Backoff(delayExpression = "${retry.delay:100}",
                  maxDelayExpression = "${retry.maxDelay:2000}",
                  multiplierExpression = "${retry.multiplier:2}"))
  public void hello(AtomicInteger counter) {
    if (counter.incrementAndGet() < 10) {
      throw new IllegalStateException();
    }
  }

  @Recover
  public void helloRecover(AtomicInteger counter) {
    counter.set(1000);
  }

  @Recover
  public void helloRecover(IllegalStateException e, AtomicInteger counter) {
    counter.set(2000);
  }

}
