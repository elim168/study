package com.elim.learn.spring.cloud.config.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Elim
 * 2019/3/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ClientTest {

  @Value("${info.foo:}")
  private String infoFoo;

  @Value("${info.foo2:}")
  private String infoFoo2;

  @Value("${hello:}")
  private String hello;

  @Value(("${hello.encrypt:}"))
  private String encryptedValue;

  @Test
  public void test() {
    Assert.assertEquals("world", this.hello);
    //远程没有的时候以本地的配置为准，远程存在时远程的属性优先级更高
    System.out.println(this.infoFoo + "----------" + this.infoFoo2);
    Assert.assertEquals("AAA", this.infoFoo);
//    Assert.assertEquals("bar", this.infoFoo);
  }

  @Test
  public void testEncrypt() {
    Assert.assertEquals("ABCD", this.encryptedValue);
  }

}
