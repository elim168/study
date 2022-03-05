package com.elim.study.sentinel.basic;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
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

/**
 * 由相应的ProcessorSlot接入
 */
public class FlowRuleTest {

  private String resourceName = "abc";

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

  /**
   * try-resource写法。
   * @throws InterruptedException
   */
  @Test
  public void testFlowControl2() throws InterruptedException {
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
        try (Entry entry = SphU.entry(resourceName)){
          TimeUnit.MILLISECONDS.sleep(500);
          succeedCount.incrementAndGet();
          entry.exit();
        } catch (BlockException e) {
          failCount.incrementAndGet();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          latch.countDown();
        }
      }).start();
    }
    latch.await();
    System.out.println("threadLimit=" + threadLimit + ", threads=" + threads + ", succeedCount=" + succeedCount + ", failCount=" + failCount);
  }

  /**
   * 使用SphO.entry，它在资源不可用时不会抛出异常，而是返回false。
   * @throws InterruptedException
   */
  @Test
  public void testFlowControl3() throws InterruptedException {
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
        if (SphO.entry(resourceName)) {
          try {
            TimeUnit.MILLISECONDS.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          succeedCount.incrementAndGet();
          SphO.exit();
        } else {
          failCount.incrementAndGet();
        }
        latch.countDown();
      }).start();
    }
    latch.await();
    System.out.println("threadLimit=" + threadLimit + ", threads=" + threads + ", succeedCount=" + succeedCount + ", failCount=" + failCount);
  }



}
