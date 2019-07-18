package com.elim.study.dubbo.test;

import com.elim.study.dubbo.anno.AnnotationConfiguration;
import com.elim.study.dubbo.anno.client.ClientAnnotationConfiguration;
import com.elim.study.dubbo.anno.client.service.HelloService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Elim
 * 19-7-16
 */
public class AnnotationTest {

  @Test
  public void provider() throws Exception {
    new AnnotationConfigApplicationContext(AnnotationConfiguration.class);
    System.in.read();
  }

  @Test
  public void consumer() {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClientAnnotationConfiguration.class);
    HelloService helloService = applicationContext.getBean(HelloService.class);
    long t1 = System.currentTimeMillis();
    helloService.sayHello("Elim123");
    System.out.println(System.currentTimeMillis() - t1);
  }

}
