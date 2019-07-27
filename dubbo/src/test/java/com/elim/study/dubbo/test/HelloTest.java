package com.elim.study.dubbo.test;

import com.elim.study.dubbo.UserContext;
import com.elim.study.dubbo.model.Person;
import com.elim.study.dubbo.service.HelloService;
import com.elim.study.dubbo.service.PersonService;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

public class HelloTest {

    @Test
    public void provider() throws Exception {
        System.setProperty("dubbo.properties.file", "dubbo2.properties");
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
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> helloService.sayHello("Elim"));
//            executorService.execute(() -> helloService2.sayHello("Elim"));
        }
        TimeUnit.SECONDS.sleep(60);
    }

    @Test
    public void testCallback() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/hello-client.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        helloService.update("Elim", resp -> System.out.println("回调的传参是：" + resp));
    }

    @Test
    public void testPerson() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/hello-client.xml");
        PersonService personService = applicationContext.getBean(PersonService.class);
        long t1 = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int count = 100;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i=0; i<count; i++) {
            Long id = Long.valueOf(i);
            executorService.execute(() -> {
                Person person = personService.findById(id);
                System.out.println(person);
                latch.countDown();
            });
        }
        latch.await();
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
    }

}
