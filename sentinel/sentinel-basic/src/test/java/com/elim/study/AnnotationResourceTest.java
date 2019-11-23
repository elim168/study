package com.elim.study;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elim
 * 19-11-23
 */
public class AnnotationResourceTest {

  private String resourceName = "hello";

  @Test
  public void test() throws InterruptedException {
    int threadLimit = 5;
    FlowRule rule = new FlowRule(resourceName);
    rule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
    rule.setCount(threadLimit);
    FlowRuleManager.loadRules(Arrays.asList(rule));


    int threads = 100;
    CountDownLatch latch = new CountDownLatch(threads);
    AtomicInteger succeedCount = new AtomicInteger();
    AtomicInteger failCount = new AtomicInteger();

    AnnotationResource resource = new AnnotationResource();

    for (int i=0; i<threads; i++) {
      new Thread(() -> {
        try {
          resource.sayHello();
          succeedCount.incrementAndGet();
        } catch (Exception e) {
          failCount.incrementAndGet();
        } finally {
          latch.countDown();
        }
      }).start();
    }
    latch.await();
    System.out.println("threadLimit=" + threadLimit + ", threads=" + threads + ", succeedCount=" + succeedCount + ", failCount=" + failCount);

  }


}
