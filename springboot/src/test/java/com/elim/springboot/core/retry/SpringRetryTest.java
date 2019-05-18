package com.elim.springboot.core.retry;

import org.junit.Assert;
import org.junit.Test;
import org.python.google.common.collect.Maps;
import org.springframework.retry.*;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.backoff.UniformRandomBackOffPolicy;
import org.springframework.retry.policy.*;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

import java.time.LocalDateTime;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
  public void testSimpleRetryPolicy() throws Exception {
    Map<Class<? extends Throwable>, Boolean> retryableExceptions = Maps.newHashMap();
    retryableExceptions.put(IllegalFormatException.class, false);
    RetryPolicy retryPolicy = new SimpleRetryPolicy(10, retryableExceptions, false, true);
    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(retryPolicy);
    AtomicInteger counter = new AtomicInteger();
    retryTemplate.execute(retryContext -> {
      if (counter.incrementAndGet() < 3) {
        throw new IllegalStateException();
      } else if (counter.incrementAndGet() < 6) {
        throw new IllegalArgumentException();
      }
      return counter.get();
    });
  }

  @Test
  public void testRetryPolicy() throws Exception {
    ExceptionClassifierRetryPolicy retryPolicy = new ExceptionClassifierRetryPolicy();

    Map<Class<? extends Throwable>, RetryPolicy> policyMap = Maps.newHashMap();
    policyMap.put(IllegalStateException.class, new SimpleRetryPolicy(5));
    policyMap.put(IllegalArgumentException.class, new SimpleRetryPolicy(4));
    retryPolicy.setPolicyMap(policyMap);

    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(retryPolicy);
    AtomicInteger counter = new AtomicInteger();
    Integer result = retryTemplate.execute(retryContext -> {
      if (counter.incrementAndGet() < 5) {
        throw new IllegalStateException();
      } else if (counter.get() < 10) {
        System.out.println(counter);
        throw new IllegalArgumentException();
      }
      return counter.get();
    });
    Assert.assertEquals(10, result.intValue());
  }

  @Test
  public void testCircuitBreakerRetryPolicy() throws Exception {
    SimpleRetryPolicy delegate = new SimpleRetryPolicy(5);
    //底层允许最多尝试5次
    CircuitBreakerRetryPolicy retryPolicy = new CircuitBreakerRetryPolicy(delegate);
    retryPolicy.setOpenTimeout(2000);//断路器打开的时间
    retryPolicy.setResetTimeout(15000);//时间窗口
    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(retryPolicy);
    AtomicInteger counter = new AtomicInteger();
    RetryState retryState = new DefaultRetryState("key");
    for (int i = 0; i < 5; i++) {
      try {
        retryTemplate.execute(retryContext -> {
          System.out.println(LocalDateTime.now() + "----" + counter.get());
          TimeUnit.MILLISECONDS.sleep(100);
          if (counter.incrementAndGet() > 0) {
            throw new IllegalStateException();
          }
          return 1;
        }, null, retryState);
      } catch (Exception e) {

      }
    }
  }

  @Test
  public void testCompositeRetryPolicy() {
    CompositeRetryPolicy compositeRetryPolicy = new CompositeRetryPolicy();
    RetryPolicy policy1 = new SimpleRetryPolicy(5);
    TimeoutRetryPolicy policy2 = new TimeoutRetryPolicy();
    policy2.setTimeout(2000);
    RetryPolicy[] policies = new RetryPolicy[]{policy1, policy2};
    compositeRetryPolicy.setPolicies(policies);
    compositeRetryPolicy.setOptimistic(true);

    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(compositeRetryPolicy);
    AtomicInteger counter = new AtomicInteger();
    Integer result = retryTemplate.execute(retryContext -> {
      if (counter.incrementAndGet() < 10) {
        throw new IllegalStateException();
      }
      return counter.get();
    });
    Assert.assertEquals(10, result.intValue());
  }

@Test
public void testFixedBackOffPolicy() {

  FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
  backOffPolicy.setBackOffPeriod(1000);
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setBackOffPolicy(backOffPolicy);

  long t1 = System.currentTimeMillis();
  long t2 = retryTemplate.execute(retryContext -> {
    if (System.currentTimeMillis() - t1 < 1000) {
      throw new IllegalStateException();
    }
    return System.currentTimeMillis();
  });
  Assert.assertTrue(t2 - t1 > 1000);
  Assert.assertTrue(t2 - t1 < 1100);
}

@Test
public void testExponentialBackOffPolicy() {
  ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
  backOffPolicy.setInitialInterval(1000);
  backOffPolicy.setMaxInterval(5000);
  backOffPolicy.setMultiplier(2.0);
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setBackOffPolicy(backOffPolicy);
  int maxAttempts = 10;
  retryTemplate.setRetryPolicy(new SimpleRetryPolicy(maxAttempts));

  long t1 = System.currentTimeMillis();
  long t2 = retryTemplate.execute(retryContext -> {
    if (retryContext.getRetryCount() < maxAttempts-1) {//最后一次尝试会成功
      throw new IllegalStateException();
    }
    return System.currentTimeMillis();
  });
  long time = 0 + 1000 + 1000 * 2 + 1000 * 2 * 2 + 5000 * (maxAttempts - 4);
  Assert.assertTrue((t2-t1) - time < 100);
}

@Test
public void testExponentialRandomBackOffPolicy() {
  ExponentialRandomBackOffPolicy backOffPolicy = new ExponentialRandomBackOffPolicy();
  backOffPolicy.setInitialInterval(1000);
  backOffPolicy.setMaxInterval(5000);
  backOffPolicy.setMultiplier(2.0);
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setBackOffPolicy(backOffPolicy);
  int maxAttempts = 10;
  retryTemplate.setRetryPolicy(new SimpleRetryPolicy(maxAttempts));

  String lastAttemptTime = "lastAttemptTime";
  retryTemplate.execute(retryContext -> {
    if (retryContext.hasAttribute(lastAttemptTime)) {
      System.out.println(System.currentTimeMillis() - (Long) retryContext.getAttribute(lastAttemptTime));
    }
    retryContext.setAttribute(lastAttemptTime, System.currentTimeMillis());
    if (retryContext.getRetryCount() < maxAttempts-1) {//最后一次尝试会成功
      throw new IllegalStateException();
    }
    return System.currentTimeMillis();
  });
}

@Test
public void testUniformRandomBackOffPolicy() {
  UniformRandomBackOffPolicy backOffPolicy = new UniformRandomBackOffPolicy();
  backOffPolicy.setMinBackOffPeriod(1000);
  backOffPolicy.setMaxBackOffPeriod(3000);
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setBackOffPolicy(backOffPolicy);
  int maxAttempts = 10;
  retryTemplate.setRetryPolicy(new SimpleRetryPolicy(maxAttempts));

  String lastAttemptTime = "lastAttemptTime";
  retryTemplate.execute(retryContext -> {
    if (retryContext.hasAttribute(lastAttemptTime)) {
      System.out.println(System.currentTimeMillis() - (Long) retryContext.getAttribute(lastAttemptTime));
    }
    retryContext.setAttribute(lastAttemptTime, System.currentTimeMillis());
    if (retryContext.getRetryCount() < maxAttempts-1) {//最后一次尝试会成功
      throw new IllegalStateException();
    }
    return System.currentTimeMillis();
  });
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

@Test
public void testListener() {
  RetryTemplate retryTemplate = new RetryTemplate();
  AtomicInteger counter = new AtomicInteger();
  RetryCallback<Integer, IllegalStateException> retryCallback = retryContext -> {
    //内部默认重试策略是最多尝试3次，即最多重试两次。还不成功就会抛出异常。
    if (counter.incrementAndGet() < 3) {
      throw new IllegalStateException();
    }
    return counter.incrementAndGet();
  };

  RetryListener retryListener = new RetryListener() {
    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
      System.out.println("---open----在第一次重试时调用");
      return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
      System.out.println("close----在最后一次重试后调用（无论成功与失败）。" + context.getRetryCount());
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
      System.out.println("error----在每次调用异常时调用。" + context.getRetryCount());
    }
  };

  retryTemplate.registerListener(retryListener);
  retryTemplate.execute(retryCallback);

}

}





