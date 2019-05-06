package com.elim.learn.spring.cloud.client.service;

import brave.Tracing;
import com.elim.learn.spring.cloud.client.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.SpanName;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.annotation.ContinueSpan;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.cloud.sleuth.instrument.async.TraceCallable;
import org.springframework.cloud.sleuth.instrument.async.TraceRunnable;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 2019/4/28
 */
@Service
public class SleuthLocalService {

  @Autowired
  private ThreadPoolTaskExecutor taskExecutor;
  @Autowired
  private SleuthLocalService2 sleuthLocalService2;

  @Autowired
  private Tracing tracing;
  @Autowired
  private SpanNamer spanNamer;

  @NewSpan
  public void method1(@SpanTag("tag123") String tagValue) {
    System.out.println("添加名为tag123的tag，值为" + tagValue);
  }

  @ContinueSpan(log = "method")
  public void method2() {
    System.out.println("-----Method2-----");
  }

  @ContinueSpan(log = "method3")
  public void method3(@SpanTag(key="personId", expression = "#person.id") Person person) {
    System.out.println(person);
  }

/*  public void threads() {
    Task task = new Task();
    TraceRunnable runnable = new TraceRunnable(this.tracing, this.spanNamer, task);
    this.taskExecutor.execute(runnable);
  }*/

  public void threads() throws Exception {
    CallableTask callableTask = new CallableTask();
    TraceCallable<Integer> traceCallable = new TraceCallable<>(this.tracing, this.spanNamer, callableTask);
    Future<Integer> future = this.taskExecutor.submit(traceCallable);
    System.out.println(future.get());
  }

  @SpanName("callable-task")
  public class CallableTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
      return 100;
    }
  }

  @SpanName("AAAAAA")
  public class Task implements Runnable {

    @Override
    public void run() {
      sleuthLocalService2.method1();
    }

  }

  @Async
  @SpanName("async-task-1")
  public void asyncTask() throws Exception {
    TimeUnit.MILLISECONDS.sleep(100);
  }

  @Scheduled(fixedDelay = 5000)
  public void scheduleTask() throws Exception {
    System.out.println(LocalDateTime.now());
    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500) + 10);
  }

}
