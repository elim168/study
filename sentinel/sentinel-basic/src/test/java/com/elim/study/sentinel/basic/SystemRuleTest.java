package com.elim.study.sentinel.basic;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 系统规则只对EntryType.IN生效，默认的Entry类型是OUT。
 * @author Elim
 * 19-11-30
 */
public class SystemRuleTest {

  private String resource = "hello";

  @Test
  public void test() {
  //设定的多个值，只要有一个达到了阈值就会触发blocked。
    SystemRule rule = new SystemRule();
    rule.setAvgRt(1000);
    rule.setHighestCpuUsage(0.8);//整体CPU使用
    rule.setHighestSystemLoad(3);//负载指系统的繁忙程度，即在等待处理的任务数
    rule.setMaxThread(20);
    rule.setQps(5);
    SystemRuleManager.loadRules(Arrays.asList(rule));
    SystemRuleManager.loadSystemConf(rule);


    for (int i=0; i<100; i++) {
      new Thread(() -> {
        while (true) {
          foo();
        }
      }).start();
    }

    this.sleep(1000 * 5);

  }


  private void foo() {
    try {
      Entry entry = SphU.entry(this.resource, EntryType.IN);
//      System.out.println("foo() executed successful");
      this.sleep(100);
      entry.exit();
    } catch (BlockException e) {
      System.out.println("Blocked-----------------" + e);
    }
  }

  private void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
