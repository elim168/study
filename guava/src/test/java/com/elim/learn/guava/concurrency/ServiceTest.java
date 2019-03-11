package com.elim.learn.guava.concurrency;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 19-3-11
 */
public class ServiceTest {

  @Test
  public void testAbstractIdleService() {
    Service service = new Service1();
    service.addListener(new Service.Listener() {
      @Override
      public void starting() {
        super.starting();
        System.out.println("监听到服务正在启动");
      }

      @Override
      public void running() {
        super.running();
        System.out.println("监听到服务正在运行");

        //其它状态的监听可以实现对应的方法。
      }
    }, Executors.newFixedThreadPool(5));
    service.startAsync();

    service.stopAsync();
  }

  @Test
  public void testAbstractExecutionThreadService() throws InterruptedException {
    Service service = new Service2();
    service.startAsync();
    TimeUnit.SECONDS.sleep(5);
    service.stopAsync();
    TimeUnit.SECONDS.sleep(2);
  }

  @Test
  public void testAbstractScheduledService() throws InterruptedException {
    Service service = new Service3();
    service.startAsync();
    TimeUnit.SECONDS.sleep(5);
    System.out.println(service.state());
    service.stopAsync();
    TimeUnit.SECONDS.sleep(1);
    System.out.println("Over");
  }

  @Test
  public void testAbstractService() throws InterruptedException {
    Service service = new Service4();
    service.startAsync();
    TimeUnit.SECONDS.sleep(5);
    System.out.println(service.state());
    service.stopAsync();
    TimeUnit.SECONDS.sleep(1);
    System.out.println("Over");
  }

  @Test
  public void testServiceManager() throws InterruptedException {
    List<Service> services = Lists.newArrayList(new Service1(), new Service2());
    services.add(new Service3());
    services.add(new Service4());
    ServiceManager serviceManager = new ServiceManager(services);
    serviceManager.addListener(new ServiceManager.Listener() {
      @Override
      public void healthy() {
        super.healthy();
        System.out.println("监听健康状况，服务都健康");
      }

      @Override
      public void stopped() {
        super.stopped();
        System.out.println("所有的服务都停止了");
      }
    });
    System.out.println("启动所有的服务");
    serviceManager.startAsync();
    TimeUnit.SECONDS.sleep(5);
    System.out.println("停止所有的服务");
    serviceManager.stopAsync();
    TimeUnit.SECONDS.sleep(1);
    System.out.println("服务都健康：" + serviceManager.isHealthy());
  }

  private static class Service1 extends AbstractIdleService {

    @Override
    protected void startUp() throws Exception {
      System.out.println("服务启动");
    }

    @Override
    protected void shutDown() throws Exception {
      System.out.println("服务关闭");
    }
  }

  private static class Service2 extends AbstractExecutionThreadService {

    @Override
    protected void run() throws Exception {
      while (super.isRunning()) {
        System.out.println("当前时间是：" + LocalDateTime.now());
        TimeUnit.SECONDS.sleep(1);
      }
    }

    //以下是官网给的示例
/*    protected void startUp() {
      dispatcher.listenForConnections(port, queue);
    }
    protected void run() {
      Connection connection;
      while ((connection = queue.take() != POISON)) {
        process(connection);
      }
    }
    protected void triggerShutdown() {
      dispatcher.stopListeningForConnections(queue);
      queue.put(POISON);
    }*/
  }

  /**
   * 周期执行
   */
  private class Service3 extends AbstractScheduledService {

    @Override
    protected void runOneIteration() throws Exception {
      System.out.println("当前时间是：" + LocalDateTime.now());
    }

    @Override
    protected Scheduler scheduler() {
      return AbstractScheduledService.Scheduler.newFixedDelaySchedule(1, 1, TimeUnit.SECONDS);
    }
  }

  private class Service4 extends AbstractService {

    @Override
    protected void doStart() {
      super.notifyStarted();
      //初始化资源，起一个线程跑任务
      Executors.newSingleThreadExecutor().execute(() -> {
        while (super.isRunning()) {
          try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(super.state() + "-当前时间是：" + LocalDateTime.now());
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
    }

    @Override
    protected void doStop() {
      super.notifyStopped();
    }
  }

}
