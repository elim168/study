package com.elim.study.dubbo.test;

import com.elim.study.dubbo.service.HelloService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        helloService.sayHello("Elim");
    }

}
