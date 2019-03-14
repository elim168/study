package com.elim.learn.guava.strings;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Elim
 * 19-3-14
 */
public class SplitterTest {

  @Test
  public void test() {
    String str = "A,B,C,D,E";
    Splitter.on(",").split(str).forEach(System.out::println);
    List<String> strings = Splitter.on(",").splitToList(str);
    Assert.assertTrue(strings.contains("A"));

    System.out.println("A,,B,C,D,".split(",").length);//最后一个空格没有
    Arrays.stream("A,,B,C,D,".split(",")).forEach(str1 -> System.out.println("---" + str1));
    Assert.assertEquals(6, Splitter.on(",").splitToList("A,,B,C,D,").size());

    int size = Splitter.on(",").trimResults().omitEmptyStrings().splitToList("A,,B,,C,D,").size();
    Assert.assertEquals(4, size);

  }

  @Test
  public void test2() {
    int size = Splitter.on(",").limit(3).splitToList("A,B,C,D,E").size();
    Assert.assertEquals(3, size);
  }

  @Test
  public void test3() {
    Map<String, String> map = Splitter.on(",").withKeyValueSeparator("|").split("A|1,B|2,C|3,D|4,E|5");
    Assert.assertEquals(5, map.size());
    Assert.assertTrue(map.containsKey("A"));
    Assert.assertEquals("1", map.get("A"));
  }

  @Test
  public void test4() {
    List<String> strs = Splitter.onPattern("\\d+").splitToList("ABC123DE12SD356");
    Assert.assertEquals(4, strs.size());
    Assert.assertTrue(strs.contains("ABC"));
    Assert.assertTrue(strs.contains("DE"));
    Assert.assertTrue(strs.contains("SD"));
    Assert.assertTrue(strs.contains(""));
  }

  @Test
  public void test5() {
    Pattern pattern = Pattern.compile("\\d+");
    List<String> strs = Splitter.on(pattern).splitToList("ABC123DE12SD356");
    System.out.println(strs);
    Assert.assertEquals(4, strs.size());
    Assert.assertTrue(strs.contains("ABC"));
    Assert.assertTrue(strs.contains("DE"));
    Assert.assertTrue(strs.contains("SD"));
    Assert.assertTrue(strs.contains(""));
  }

  @Test
  public void test6() {
    List<String> strings = Splitter.on(CharMatcher.whitespace()).splitToList("A B C D\nE F");
    System.out.println(strings);
    Assert.assertEquals(6, strings.size());

    String result = CharMatcher.anyOf("ABC").replaceFrom("AaBbCc", "*");
    Assert.assertEquals("*a*b*c", result);


    result = CharMatcher.inRange('A', 'G').removeFrom("AaBbCcDdEeFfGg");
    Assert.assertEquals("abcdefg", result);

    "ABC".getBytes(Charsets.UTF_8);
  }

}
