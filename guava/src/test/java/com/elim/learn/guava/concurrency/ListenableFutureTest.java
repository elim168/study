package com.elim.learn.guava.concurrency;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @author Elim
 * 19-3-10
 */
public class ListenableFutureTest {

  @Test
  public void test() throws InterruptedException {
    Executor directExecutor = MoreExecutors.directExecutor();
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
    ListenableFuture<String> listenableFuture = listeningExecutorService.submit(() -> LocalDateTime.now().toString());
    listenableFuture.addListener(() -> System.out.println("执行完了"), directExecutor);

    Futures.addCallback(listenableFuture, new FutureCallback<String>() {
      @Override
      public void onSuccess(@Nullable String result) {
        System.out.println("执行成功，返回值是：" + result);
      }

      @Override
      public void onFailure(Throwable t) {
        System.out.println("执行异常，" + t.getMessage());
      }
    }, directExecutor);

    TimeUnit.SECONDS.sleep(1);

  }

  @Test
  public void test2() throws InterruptedException, ExecutionException {
    ListenableFutureTask<String> listenableFutureTask = ListenableFutureTask.create(() -> LocalDateTime.now().toString());
    Futures.addCallback(listenableFutureTask, new FutureCallback<String>() {
      @Override
      public void onSuccess(@Nullable String result) {
        System.out.println("执行成功，result：" + result);
      }

      @Override
      public void onFailure(Throwable t) {
        System.out.println("执行异常");
      }
    }, MoreExecutors.directExecutor());

    //ListenableFutureTask添加的监听器不需要采用ListeningExecutorService执行也可以执行其回调方法。
    Executors.newSingleThreadExecutor().execute(listenableFutureTask);
    TimeUnit.SECONDS.sleep(1);
    System.out.println(listenableFutureTask.get());
  }


}
