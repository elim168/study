package com.elim.learn.spring.cloud.config.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Elim
 * 2019/4/16
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class EnvironmentTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void test() {
    Environment environment = this.applicationContext.getEnvironment();
    String infoFoo = environment.getProperty("info.foo");
    System.out.println(infoFoo);
    System.out.println(environment.getProperty("hello.encrypt"));
  }

}
