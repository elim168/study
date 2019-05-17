# Spring Retry介绍

Spring retry是Spring提供的一种重试机制的解决方案。它内部抽象了一个RetryOperations接口，其定义如下。

```java
public interface RetryOperations {

  <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback) throws E;

  <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback, RecoveryCallback<T> recoveryCallback) throws E;

  <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback, RetryState retryState) throws E, ExhaustedRetryException;

  <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback, RecoveryCallback<T> recoveryCallback, RetryState retryState)
          throws E;

}
```

从定义中可以看到它定义了几个重载的`execute()`，它们之间的差别就在于RetryCallback、RecoveryCallback、RetryState，其中核心参数是RetryCallback。RetryCallback的定义如下，从定义中可以看到它就定义了一个`doWithRetry()`，该方法的返回值就是RetryOperations的`execute()`的返回值，RetryCallback的范型参数中定义的Throwable是中执行可重试方法时可抛出的异常，可由外部进行捕获。

```java
public interface RetryCallback<T, E extends Throwable> {

  T doWithRetry(RetryContext context) throws E;
  
}
```

当RetryCallback不能再重试的时候，如果定义了RecoveryCallback，就会调用RecoveryCallback，并以其返回结果作为`execute()`的返回结果。其定义如下。RetryCallback和RecoverCallback定义的接口方法都可以接收一个RetryContext参数，通过它可以获取到尝试次数，也可以通过其`setAttribute()`和`getAttribute()`来传递一些信息。

```java
public interface RecoveryCallback<T> {

  T recover(RetryContext context) throws Exception;

}
```

Spring Retry包括有状态的重试和无状态的重试，对于有状态的重试，它主要用来提供一个用于在RetryContextCache中保存RetryContext的Key，这样可以在多次不同的调用中应用同一个RetryContext（无状态的重试每次发起调用都是一个全新的RetryContext，在整个重试过程中是一个RetryContext，其不会进行保存。有状态的重试因为RetryContext是保存的，其可以跨或不跨线程在多次`execute()`调用中应用同一个RetryContext）。


使用Spring Retry需要引入如下依赖。

```xml
<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
    <version>1.2.4.RELEASE</version>
</dependency>
```

Spring Retry提供了一个RetryOperations的实现，RetryTemplate，通过它我们可以发起一些可重试的请求。其内部的重试机制通过RetryPolicy来控制。RetryTemplate默认使用的是SimpleRetryPolicy实现，SimpleRetryPolicy只是简单的控制尝试几次，包括第一次调用。RetryTemplate默认使用的是尝试3次的策略。所以下面的单元策略是可以通过的，第一二次尝试都失败，此时counter变为3了，第3次尝试成功了，counter变为4了。

```java
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
```

接着来看一个使用RecoveryCallback的例子。我们把上面的例子简单改了下，改为调用包含RecoveryCallback入参的`execute()`，RetryCallback内部也改为了即使尝试了3次后仍然会失败。此时将转为调用RecoveryCallback，RecoveryCallback内部通过RetryContext获取了尝试次数，此时RetryCallback已经尝试3次了，所以RetryContext获取的尝试次数是3，RecoveryCallback的返回结果30将作为`execute()`的返回结果。

```java
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
```

## RetryPolicy

RetryTemplate内部的重试策略是由RetryPolicy控制的。RetryPolicy的定义如下。

```java
public interface RetryPolicy extends Serializable {

  /**
   * @param context the current retry status
   * @return true if the operation can proceed
   */
  boolean canRetry(RetryContext context);

  /**
   * Acquire resources needed for the retry operation. The callback is passed
   * in so that marker interfaces can be used and a manager can collaborate
   * with the callback to set up some state in the status token.
   * @param parent the parent context if we are in a nested retry.
   *
   * @return a {@link RetryContext} object specific to this policy.
   *
   */
  RetryContext open(RetryContext parent);

  /**
   * @param context a retry status created by the
   * {@link #open(RetryContext)} method of this policy.
   */
  void close(RetryContext context);

  /**
   * Called once per retry attempt, after the callback fails.
   *
   * @param context the current status object.
   * @param throwable the exception to throw
   */
  void registerThrowable(RetryContext context, Throwable throwable);

}
```

### SimpleRetryPolicy

RetryTemplate内部默认时候用的是SimpleRetryPolicy。SimpleRetryPolicy默认将对所有异常进行尝试，最多尝试3次。如果需要调整使用的RetryPolicy，可以通过RetryTemplate的`setRetryPolicy()`进行设置。比如下面代码就显示的设置了需要使用的RetryPolicy是不带参数的SimpleRetryPolicy，其默认会尝试3次。

```java
public void testSimpleRetryPolicy() {
  RetryPolicy retryPolicy = new SimpleRetryPolicy();
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setRetryPolicy(retryPolicy);
  AtomicInteger counter = new AtomicInteger();
  Integer result = retryTemplate.execute(retryContext -> {
    if (counter.incrementAndGet() < 3) {
      throw new IllegalStateException();
    }
    return counter.get();
  });
  Assert.assertEquals(3, result.intValue());
}
```

如果希望最多尝试10次，只需要传入构造参数10即可，比如下面这样。

```java
RetryPolicy retryPolicy = new SimpleRetryPolicy(10);
```

在实际使用的过程中，可能你不会希望所有的异常都进行重试，因为有的异常重试是解决不了问题的。所以可能你会想要指定可以重试的异常类型。通过SimpleRetryPolicy的构造参数可以指定哪些异常是可以进行重试的。比如下面代码我们指定了最多尝试10次，且只有IllegalStateException是可以进行重试的。那么在运行下面代码时前三次抛出的IllegalStateException都会再次进行尝试，第四次会抛出IllegalArgumentException，此时不能继续尝试了，该异常将会对外抛出。

```java
@Test
public void testSimpleRetryPolicy() {
  Map<Class<? extends Throwable>, Boolean> retryableExceptions = Maps.newHashMap();
  retryableExceptions.put(IllegalStateException.class, true);
  RetryPolicy retryPolicy = new SimpleRetryPolicy(10, retryableExceptions);
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
```

看到这里可能你会有疑问，可以进行重试的异常定义为什么使用的是Map结构，而不是简单的通过Set或List来定义可重试的所有异常类似，而要多一个Boolean类型的Value来定义该异常是否可重试。这样做的好处是它可以实现包含/排除的逻辑，比如下面这样，我们可以指定对所有的RuntimeException都是可重试的，唯独IllegalArgumentException是一个例外。所以当你运行如下代码时其最终结果还是抛出IllegalArgumentException。

```java
@Test
public void testSimpleRetryPolicy() {
  Map<Class<? extends Throwable>, Boolean> retryableExceptions = Maps.newHashMap();
  retryableExceptions.put(RuntimeException.class, true);
  retryableExceptions.put(IllegalArgumentException.class, false);
  RetryPolicy retryPolicy = new SimpleRetryPolicy(10, retryableExceptions);
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
```

SimpleRetryPolicy在判断一个异常是否可重试时，默认会取最后一个抛出的异常。我们通常可能在不同的业务层面包装不同的异常，比如有些场景我们可能需要把捕获到的异常都包装为BusinessException，比如说把一个IllegalStateException包装为BusinessException。我们程序中定义了所有的IllegalStateException是可以进行重试的，如果SimpleRetryPolicy直接取的最后一个抛出的异常会取到BusinessException。这可能不是我们想要的，此时可以通过构造参数traverseCauses指定可以遍历异常栈上的每一个异常进行判断。比如下面代码，在`traverseCauses=false`时，只会在抛出IllegalStateException时尝试3次，第四次抛出的Exception不是RuntimeException，所以不会进行重试。指定了`traverseCauses=true`时第四次尝试时抛出的Exception，再往上找时会找到IllegalArgumentException，此时又可以继续尝试，所以最终执行后counter的值会是6。

```java
@Test
public void testSimpleRetryPolicy() throws Exception {
  Map<Class<? extends Throwable>, Boolean> retryableExceptions = Maps.newHashMap();
  retryableExceptions.put(RuntimeException.class, true);
  RetryPolicy retryPolicy = new SimpleRetryPolicy(10, retryableExceptions, true);
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setRetryPolicy(retryPolicy);
  AtomicInteger counter = new AtomicInteger();
  retryTemplate.execute(retryContext -> {
    if (counter.incrementAndGet() < 3) {
      throw new IllegalStateException();
    } else if (counter.incrementAndGet() < 6) {
      try {
        throw new IllegalArgumentException();
      } catch (Exception e) {
        throw new Exception(e);
      }
    }
    return counter.get();
  });
}
```

SimpleRetryPolicy除了前面介绍的3个构造方法外，还有如下这样一个构造方法，它的第四个参数表示当抛出的异常是在retryableExceptions中没有定义是否需要尝试时其默认的值，该值为true则表示默认可尝试。

```java
public SimpleRetryPolicy(int maxAttempts, Map<Class<? extends Throwable>, Boolean> retryableExceptions,
                         boolean traverseCauses, boolean defaultValue)
```

下面代码中通过retryableExceptions指定了抛出IllegalFormatException时不进行重试，然后通过SimpleRetryPolicy的第四个参数指定了其它异常默认是可以进行重试的。所以下面的代码也可以正常运行，运行结束后counter的值是6。

```java
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
```

### AlwaysRetryPolicy

顾名思义就是一直重试，直到成功为止。所以对于下面代码而言，其会一直尝试100次，第100次的时候它就成功了。

```java
@Test
public void testRetryPolicy() {
  RetryPolicy retryPolicy = new AlwaysRetryPolicy();
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setRetryPolicy(retryPolicy);
  AtomicInteger counter = new AtomicInteger();
  Integer result = retryTemplate.execute(retryContext -> {
    if (counter.incrementAndGet() < 100) {
      throw new IllegalStateException();
    }
    return counter.get();
  });
  Assert.assertEquals(100, result.intValue());
}
```

### NeverRetryPolicy

与AlwaysRetryPolicy相对的一个极端是从不重试，NeverRetryPolicy的策略就是从不重试，但是第一次调用还是会发生的。所以对于下面代码而言，如果第一次获取的随机数不是3的倍数，则可以正常执行，否则将抛出IllegalStateException。

```java
@Test
public void testRetryPolicy() {
  RetryPolicy retryPolicy = new NeverRetryPolicy();
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setRetryPolicy(retryPolicy);
  retryTemplate.execute(retryContext -> {
    int value = new Random().nextInt(100);
    if (value % 3 == 0) {
      throw new IllegalStateException();
    }
    return value;
  });
}
```

### TimeoutRetryPolicy

TimeoutRetryPolicy用于在指定时间范围内进行重试，直到超时为止，默认的超时时间是1000毫秒。

```java
@Test
public void testRetryPolicy() throws Exception {
  TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
  retryPolicy.setTimeout(2000);//不指定时默认是1000
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setRetryPolicy(retryPolicy);
  AtomicInteger counter = new AtomicInteger();
  Integer result = retryTemplate.execute(retryContext -> {
    if (counter.incrementAndGet() < 10) {
      TimeUnit.MILLISECONDS.sleep(20);
      throw new IllegalStateException();
    }
    return counter.get();
  });
  Assert.assertEquals(10, result.intValue());
}

```

### ExceptionClassifierRetryPolicy

之前介绍的SimpleRetryPolicy可以基于异常来判断是否需要进行重试。如果你需要基于不同的异常应用不同的重试策略怎么办呢？ExceptionClassifierRetryPolicy可以帮你实现这样的需求。下面的代码中我们就指定了当捕获的是IllegalStateException时将最多尝试5次，当捕获的是IllegalArgumentException时将最多尝试4次。其执行结果最终是抛出IllegalArgumentException的，但是在最终抛出IllegalArgumentException时counter的值是多少呢？换句话说它一共尝试了几次呢？答案是8次。按照笔者的写法，进行第5次尝试时不会抛出IllegalStateException，而是抛出IllegalArgumentException，它对于IllegalArgumentException的重试策略而言是第一次尝试，之后会再尝试3次，5+3=8，所以counter的最终的值是8。

```java
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
  retryTemplate.execute(retryContext -> {
    if (counter.incrementAndGet() < 5) {
      throw new IllegalStateException();
    } else if (counter.get() < 10) {
      throw new IllegalArgumentException();
    }
    return counter.get();
  });
}
```

### CircuitBreakerRetryPolicy

CircuitBreakerRetryPolicy是包含了断路器功能的RetryPolicy，它内部默认包含了一个SimpleRetryPolicy，最多尝试3次。在固定的时间窗口内（默认是20秒）如果底层包含的RetryPolicy的尝试次数都已经耗尽了，则其会打开断路器，默认打开时间是5秒，在这段时间内如果还有其它请求过来就不会再进行调用了。CircuitBreakerRetryPolicy需要跟RetryState一起使用，下面的代码中RetryTemplate使用的是CircuitBreakerRetryPolicy，一共调用了5次`execute()`，每次调用RetryCallback都会抛出IllegalStateException，并且会打印counter的当前值，前三次RetryCallback都是可以运行的，之后断路器打开了，第四五次执行`execute()`时就不会再执行RetryCallback了，所以你只能看到只进行了3次打印。

```java
@Test
public void testCircuitBreakerRetryPolicy() throws Exception {
  CircuitBreakerRetryPolicy retryPolicy = new CircuitBreakerRetryPolicy();
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setRetryPolicy(retryPolicy);
  AtomicInteger counter = new AtomicInteger();
  RetryState retryState = new DefaultRetryState("key");
  for (int i=0; i<5; i++) {
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
```

断路器默认打开的时间是5秒，5秒之后断路器又会关闭，RetryCallback又可以正常调用了。判断断路器是否需要打开的时间窗口默认是20秒，即在20秒内所有的尝试次数都用完了，就会打开断路器。如果在20秒内只尝试了两次（默认3次），则在新的时间窗口内尝试次数又将从0开始计算。可以通过如下方式进行这两个时间的设置。

```java
  SimpleRetryPolicy delegate = new SimpleRetryPolicy(5);
  //底层允许最多尝试5次
  CircuitBreakerRetryPolicy retryPolicy = new CircuitBreakerRetryPolicy(delegate);
  retryPolicy.setOpenTimeout(2000);//断路器打开的时间
  retryPolicy.setResetTimeout(15000);//时间窗口
```

### CompositeRetryPolicy

CompositeRetryPolicy可以用来组合多个RetryPolicy，可以设置必须所有的RetryPolicy都是可以重试的时候才能进行重试，也可以设置只要有一个RetryPolicy可以重试就可以进行重试。默认是必须所有的RetryPolicy都可以重试才能进行重试。下面代码中应用的就是CompositeRetryPolicy，它组合了两个RetryPolicy，最多尝试5次的SimpleRetryPolicy和超时时间是2秒钟的TimeoutRetryPolicy，所以它们的组合就是必须尝试次数不超过5次且尝试时间不超过2秒钟才能进行重试。`execute()`中执行的RetryCallback的逻辑是counter的值小于10时就抛出IllegalStateException，否则就返回counter的值。第一次尝试的时候会失败，第二次也是，直到第5次尝试也还是失败的，此时SimpleRetryPolicy已经不能再尝试了，而TimeoutRetryPolicy此时还是可以尝试的，但是由于前者已经不能再尝试了，所以整体就不能再尝试了。所以下面的执行会以抛出IllegalStateException告终。

```java
@Test
public void testCompositeRetryPolicy() {
  CompositeRetryPolicy compositeRetryPolicy = new CompositeRetryPolicy();
  RetryPolicy policy1 = new SimpleRetryPolicy(5);
  TimeoutRetryPolicy policy2 = new TimeoutRetryPolicy();
  policy2.setTimeout(2000);
  RetryPolicy[] policies = new RetryPolicy[]{policy1, policy2};
  compositeRetryPolicy.setPolicies(policies);

  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setRetryPolicy(compositeRetryPolicy);
  AtomicInteger counter = new AtomicInteger();
  retryTemplate.execute(retryContext -> {
    if (counter.incrementAndGet() < 10) {
      throw new IllegalStateException();
    }
    return counter.get();
  });
}
```

CompositeRetryPolicy也支持组合的RetryPolicy中只要有一个RetryPolicy满足条件就可以进行重试，这是通过参数optimistic控制的，默认是false，改为true即可。比如下面设置了`setOptimistic(true)`，那么中尝试5次后SimpleRetryPolicy已经不满足了，但是TimeoutRetryPolicy还满足条件，所以最终会一直尝试，直到counter的值为10。

```java
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
```

## BackOffPolicy

BackOffPolicy用来定义在两次尝试之间需要间隔的时间，RetryTemplate内部默认使用的是NoBackOffPolicy，其在两次尝试之间不会进行任何的停顿。对于一般可重试的操作往往是基于网络进行的远程请求，它可能由于网络波动暂时不可用，如果立马进行重试它可能还是不可用，但是停顿一下，过一会再试可能它又恢复正常了，所以在RetryTemplate中使用BackOffPolicy往往是很有必要的。

### FixedBackOffPolicy

FixedBackOffPolicy将在两次重试之间进行一次固定的时间间隔，默认是1秒钟，也可以通过`setBackOffPeriod()`进行设置。下面代码中指定了两次重试的时间间隔是1秒钟，第一次尝试会失败，等一秒后会进行第二次尝试，第二次尝试会成功。

```java
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
```

### ExponentialBackOffPolicy

ExponentialBackOffPolicy可以使每一次尝试的间隔时间都不一样，它有3个重要的参数，初始间隔时间、后一次间隔时间相对于前一次间隔时间的倍数和最大的间隔时间，它们的默认值分别是100毫秒、2.0和30秒。下面的代码使用了ExponentialBackOffPolicy，指定了初始间隔时间是1000毫秒，每次间隔时间以2倍的速率递增，最大的间隔时间是5000毫秒，它最多可以尝试10次。所以当第1次尝试失败后会间隔1秒后进行第2次尝试，之后再间隔2秒进行第3次尝试，之后再间隔4秒进行第4次尝试，之后都是间隔5秒再进行下一次尝试，因为再翻倍已经超过了设定的最大的间隔时间。

```java
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
```