package com.elim.study.sentinel.basic;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elim
 * 19-11-26
 */
public class DegradeTest {

  String resource = "hello";

  @Test
  public void test() throws Exception {
    DegradeRule rule = new DegradeRule();
    rule.setCount(500);
    rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
    rule.setResource(this.resource);
    rule.setTimeWindow(10);
    DegradeRuleManager.loadRules(Arrays.asList(rule));

    int threads = 30;

    AtomicInteger successCount = new AtomicInteger();
    AtomicInteger failCount = new AtomicInteger();

    for (int i=0; i<threads; i++) {
      new Thread(() -> {
        while (true) {
          sleep(100);
          try {
            Entry entry = SphU.entry(resource);
            successCount.incrementAndGet();
            sleep(600);
            entry.exit();
          } catch (BlockException e) {
            failCount.incrementAndGet();
          }
        }
      }).start();
    }

    Thread thread = new Thread(() -> {
      int times = 60;
      for (int i = 0; i < times; i++) {
        sleep(1000);
        System.out.println(i + "-----------" + successCount + "," + failCount);
      }
    });
    thread.start();
    thread.join();

  }

  private void sleep(long milliSeconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliSeconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}
