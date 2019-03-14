package com.elim.learn.guava.strings;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Elim
 * 19-3-12
 */
public class JoinerTest {

  @Test
  public void test() {
    //忽略null，但不忽略空字符串
    String result = Joiner.on(",").skipNulls().join("A", "B", null, "C", "");
    Assert.assertEquals("A,B,C,", result);
  }

  @Test
  public void test2() {
    //遇到null用字符串A代替
    String result = Joiner.on(",").useForNull("A").join("A", "B", null, "C");
    Assert.assertEquals("A,B,A,C", result);
  }

  @Test
  public void test3() {
    String result = Joiner.on(",").join("A", 2, 3, 4);
    Assert.assertEquals("A,2,3,4", result);

    Assert.assertEquals(result, Joiner.on(",").join(Arrays.asList("A", 2, 3, 4)));
  }

  @Test
  public void testAppendTo() throws IOException {
    Joiner.on(",").appendTo(System.out, "1", "2", "3");//输出1,2,3
  }

  @Test
  public void testAppendTo2() {
    StringBuilder builder = new StringBuilder();
    StringBuilder builder2 = Joiner.on(",").appendTo(builder, "A", "B", "C");
    Assert.assertEquals("A,B,C", builder.toString());
    Assert.assertTrue(builder == builder2);
  }

  @Test
  public void test4() {
    ImmutableMap<String, Integer> map = ImmutableMap.of("A", 1, "B", 2, "C", 3);
    String result = Joiner.on(",").withKeyValueSeparator("|").join(map);
    System.out.println(result);
    Assert.assertEquals("A|1,B|2,C|3", result);
  }

}
