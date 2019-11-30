package com.elim.study.sentinel.basic;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Elim
 * 19-11-30
 */
public class AuthorityRuleTest {

  private String resource = "hello";
  private String app1 = "app1";
  private String app2 = "app2";

  @Test
  public void testWhite() {
    this.initWhiteRule();
    Assert.assertTrue(this.busiMethod(this.app1));
    Assert.assertFalse(this.busiMethod(this.app2));
  }

  @Test
  public void testBlack() {
    this.initBlackRule();
    Assert.assertFalse(this.busiMethod(this.app1));
    Assert.assertTrue(this.busiMethod(this.app2));
  }

  private void initWhiteRule() {
    AuthorityRule rule = new AuthorityRule();
    rule.setResource(this.resource);
    rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
    rule.setLimitApp(this.app1);
    AuthorityRuleManager.loadRules(Arrays.asList(rule));
  }

  private void initBlackRule() {
    AuthorityRule rule = new AuthorityRule();
    rule.setResource(this.resource);
    rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
    rule.setLimitApp(this.app1);
    AuthorityRuleManager.loadRules(Arrays.asList(rule));
  }

  private boolean busiMethod(String app) {
    try {
      ContextUtil.enter(this.resource, app);
      Entry entry = SphU.entry(this.resource);
      System.out.println("--------busiMethod executed successful");
      entry.exit();
      return true;
    } catch (BlockException e) {
      System.out.println("Blocked ----------- " + e);
      return false;
    } finally {
      ContextUtil.exit();
    }
  }


}
