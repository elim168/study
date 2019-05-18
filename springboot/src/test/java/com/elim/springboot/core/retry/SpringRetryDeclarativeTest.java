package com.elim.springboot.core.retry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elim
 * 19-5-18
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RetryConfiguration.class)
public class SpringRetryDeclarativeTest {

  @Autowired
  public HelloService helloService;

  @Test
  public void test() {
    AtomicInteger counter = new AtomicInteger();
    this.helloService.hello(counter);
    System.out.println(counter.get());
  }

}
