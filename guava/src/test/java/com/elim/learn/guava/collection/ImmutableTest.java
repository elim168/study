package com.elim.learn.guava.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Elim
 * 19-2-14
 */
public class ImmutableTest {

  /**
   * 通过copyOf构造ImmutableXXX
   */
  @Test
  public void test() {
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < 5; i++) {
      set.add(i + 1);
    }
    ImmutableSet<Integer> immutableSet = ImmutableSet.copyOf(set);
    Exception exception = null;
    try {
      immutableSet.add(0);
    } catch (UnsupportedOperationException e) {
      exception = e;
    }
    Assert.assertTrue(exception instanceof UnsupportedOperationException);
  }

  /**
   * 通过of构造ImmutableXXX
   */
  @Test
  public void test2() {
    ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5, 6, 7);
    ImmutableSet.of(1, 2, 3, 4, 5, 6, 7);
    ImmutableSet.copyOf(list);
    ImmutableMap<String, String> map = ImmutableMap.of("key1", "value1", "key2", "value2", "keyN", "valueN");
    Assert.assertEquals(3, map.size());
  }

  /**
   * 通过builder构造ImmutableXXX
   */
  @Test
  public void test3() {
    ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
    ImmutableSet<Integer> set = ImmutableSet.<Integer>builder().addAll(list).add(4, 5, 6).build();
    Assert.assertEquals(6, set.size());
  }

  @Test
  public void test4() {
    ImmutableSet<Integer> set = ImmutableSet.of(4, 3, 2, 1, 0);
    ImmutableList<Integer> list = set.asList();
    System.out.println(list);
  }

}
