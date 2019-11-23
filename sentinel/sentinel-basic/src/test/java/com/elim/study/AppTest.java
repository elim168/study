package com.elim.study;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {

  private String resourceName = "abc";
  /**
   * Rigorous Test :-)
   */
  @Test
  public void shouldAnswerWithTrue() {
    assertTrue(true);
  }

  @Test
  public void testFlowControl() throws InterruptedException {
    int threadLimit = 5;
    FlowRule rule = new FlowRule(resourceName);
    rule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
    rule.setCount(threadLimit);
    FlowRuleManager.loadRules(Arrays.asList(rule));


    int threads = 100;
    CountDownLatch latch = new CountDownLatch(threads);
    AtomicInteger succeedCount = new AtomicInteger();
    AtomicInteger failCount = new AtomicInteger();
    for (int i=0; i<threads; i++) {
      new Thread(() -> {
        Entry entry = null;
        try {
          entry = SphU.entry(resourceName);
          TimeUnit.MILLISECONDS.sleep(500);
          succeedCount.incrementAndGet();
        } catch (BlockException e) {
//          e.printStackTrace();
          failCount.incrementAndGet();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          latch.countDown();
          if (entry != null) {
            entry.exit();
          }
        }
      }).start();
    }
    latch.await();
    System.out.println("threadLimit=" + threadLimit + ", threads=" + threads + ", succeedCount=" + succeedCount + ", failCount=" + failCount);
  }


}
