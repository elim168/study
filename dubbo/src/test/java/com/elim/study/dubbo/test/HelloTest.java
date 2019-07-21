package com.elim.study.dubbo.test;

import com.elim.study.dubbo.service.HelloService;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        RpcContext.getContext().setAttachment("ABC", "123");
        helloService.sayHello("Elim");
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
