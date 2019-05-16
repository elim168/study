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
