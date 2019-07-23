package com.elim.study.dubbo.test;

import com.elim.study.dubbo.UserContext;
import com.elim.study.dubbo.service.HelloService;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloTest {

    @Test
    public void provider() throws Exception {
        new ClassPathXmlApplicationContext("/hello-server.xml");
        System.in.read();
    }

    @Test
    public void testConsumer() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/hello-client.xml");
        HelloService helloService = applicationContext.getBean(HelloService.class);
        UserContext.setUserId(123456L);
        RpcContext.getContext().setAttachment("ABC", "123");
        String result = helloService.sayHello("Elim");
        System.out.println(result);
    }

    @Test
    public void testAsync() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/hello-client.xml");
        HelloService helloService = applicationContext.getBean(HelloService.class);
        CompletableFuture<String> future = helloService.sayHelloAsync("Elim");
        future.whenComplete((r, e) -> {
           if (e != null) {
               System.out.println("执行出错------" + e);
           } else {
               System.out.println(Thread.currentThread() + "执行正常------" + r);
           }
        });
        System.out.println("************************");
    }

@Test
public void testAsync2() {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/hello-client.xml");
    HelloService helloService = applicationContext.getBean(HelloService.class);
    String result = helloService.sayHello("Elim");
    assert result == null;
    CompletableFuture<String> future = RpcContext.getContext().getCompletableFuture();
    future.whenComplete((r, e) -> {
        if (e != null) {
            System.out.println("远程调用异常：" + e);
        } else {
            System.out.println("远程调用成功：" + r);
        }
    });
}

@Test
public void testAsync3() {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/hello-client.xml");
    HelloService helloService = applicationContext.getBean(HelloService.class);
    CompletableFuture<String> future = RpcContext.getContext().asyncCall(() -> helloService.sayHello("Elim"));
    future.whenComplete((r, e) -> {
        if (e != null) {
            System.out.println("远程调用异常：" + e);
        } else {
            System.out.println("远程调用成功：" + r);
        }
    });
}

    @Test
    public void testConsumer2() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/hello-client.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        HelloService helloService2 = applicationContext.getBean("helloService2", HelloService.class);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0; i<3; i++) {
            executorService.execute(() -> helloService.sayHello("Elim"));
//            executorService.execute(() -> helloService2.sayHello("Elim"));
        }
        TimeUnit.SECONDS.sleep(60);
    }

}
