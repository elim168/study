package com.elim.springboot.core.retry;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elim
 * 19-5-15
 */
public class SpringRetryTest {

  @Test
  public void test() {
    RetryTemplate retryTemplate = new RetryTemplate();
    AtomicInteger counter = new AtomicInteger();
    RetryCallback<Integer, IllegalStateException> retryCallback = retryContext -> {
      if (counter.incrementAndGet() < 3) {//内部默认重试策略是最多尝试3次，即最多重试两次。
        throw new IllegalStateException();
      }
      return counter.incrementAndGet();
    };
    Integer result = retryTemplate.execute(retryCallback);

    Assert.assertEquals(4, result.intValue());
  }

  @Test
  public void testRecoveryCallback() {

    RetryTemplate retryTemplate = new RetryTemplate();
    AtomicInteger counter = new AtomicInteger();
    RetryCallback<Integer, IllegalStateException> retryCallback = retryContext -> {
      //内部默认重试策略是最多尝试3次，即最多重试两次。还不成功就会抛出异常。
      if (counter.incrementAndGet() < 10) {
        throw new IllegalStateException();
      }
      return counter.incrementAndGet();
    };


    RecoveryCallback<Integer> recoveryCallback = retryContext -> {
      //返回的应该是30。RetryContext.getRetryCount()记录的是尝试的次数，一共尝试了3次。
      return retryContext.getRetryCount() * 10;
    };
    //尝试策略已经不满足了，将不再尝试的时候会抛出异常。此时如果指定了RecoveryCallback将执行RecoveryCallback，
    //然后获得返回值。
    Integer result = retryTemplate.execute(retryCallback, recoveryCallback);

    Assert.assertEquals(30, result.intValue());
  }

}
