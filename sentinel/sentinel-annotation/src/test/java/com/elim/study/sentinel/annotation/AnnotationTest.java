package com.elim.study.sentinel.annotation;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.elim.study.sentinel.annotation.service.FooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elim
 * 19-11-27
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AnnotationTest {

  @Autowired
  private FooService fooService;

  @Test
  public void test() throws InterruptedException {
    FlowRule rule = new FlowRule();
    rule.setResource(ResourceConstant.RESOURCE_1);
    rule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
    rule.setCount(10);
    FlowRuleManager.loadRules(Arrays.asList(rule));

    AtomicInteger successCount = new AtomicInteger();
    AtomicInteger failCount = new AtomicInteger();
    int threads = 20;
    for (int i=0; i<threads; i++) {
      new Thread(() -> {
        while (true) {
          sleep(100);
          try {
            fooService.resource1();
            successCount.incrementAndGet();
          } catch (Exception e) {
            if (BlockException.isBlockException(e)) {
              failCount.incrementAndGet();
            }
          }
        }
      }).start();
    }
    Thread inspectThread = new Thread(() -> {
      for (int i=0; i<60; i++) {
        System.out.println(String.format("%d------%d,%d", i, successCount.get(), failCount.get()));
        sleep(1000);
      }
    });
    inspectThread.start();
    inspectThread.join();
  }


  private void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
