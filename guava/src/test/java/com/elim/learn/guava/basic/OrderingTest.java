package com.elim.learn.guava.basic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Elim
 * 19-1-26
 */
public class OrderingTest {

  @Test
  public void test() {
    Ordering<Integer> ordering = new Ordering<Integer>() {
      @Override
      public int compare(@Nullable Integer left, @Nullable Integer right) {
        return left.compareTo(right);
      }
    };
    List<Integer> ints = Arrays.asList(9, 8, 16, 20, 30, 88, 99, 78, 25, 78);
    System.out.println(ints);
    List<Integer> thirdGreatest = ordering.greatestOf(ints, 3);//前三大的数字
    System.out.println(thirdGreatest);
    List<Integer> thirdLeast = ordering.leastOf(ints, 3);//最小的三个数字
    System.out.println(thirdLeast);
    Integer max = ordering.max(10, 20, 2, 88, 9);
    Assert.assertEquals(new Integer(88), max);

    Collections.sort(ints, ordering);
    System.out.println(ints);
  }

  @Test
  public void test2() {
    List<Integer> ints = Arrays.asList(9, 8, 16, 20, 30, 88, 99, 78, 25, 78);
    Integer min = Ordering.natural().min(ints);
    Assert.assertEquals(new Integer(8), min);
  }

  @Test
  public void test3() {
    Ordering<Integer> ordering = Ordering.explicit(8, 9, 7, 10, 12, 1, 5);
    List<Integer> ints = Arrays.asList(1, 5, 7, 8, 9);
    Collections.sort(ints, ordering);
    System.out.println(ints);
    Assert.assertEquals(new Integer(8), ints.get(0));
    Assert.assertEquals(new Integer(9), ints.get(1));
    Assert.assertEquals(new Integer(7), ints.get(2));
    Assert.assertEquals(new Integer(1), ints.get(3));
    Assert.assertEquals(new Integer(5), ints.get(4));
  }

  @Test
  public void test4() {
    Ordering<Object> ordering = Ordering.usingToString();
    List<Object> objects = Arrays.asList("ABC", "GHI", "GAB", "DDDDDD", "BCDA", 10, 20, 30, "CBA");
    Collections.sort(objects, ordering);
    System.out.println(objects);
    Collections.sort(objects, ordering.reverse());
    System.out.println(objects);
    Collections.sort(objects, ordering.onResultOf(a -> a.toString().length()).reverse().compound(Ordering.usingToString()));
    System.out.println(objects);
    Collections.sort(objects, ordering.onResultOf(a -> a.toString().length()).compound(Ordering.usingToString()).reverse());
    System.out.println(objects);
  }

  @Test
  public void test5() {
    //基于Comparator对象创建Ordering
    Ordering<Integer> ordering = Ordering.<Integer>from((left, right) -> left.compareTo(right));
    List<Integer> nums = ImmutableList.of(1, 2, 3, 9, 8, 7, 6);
    nums = new ArrayList<>(nums);
    Collections.sort(nums, ordering);
    System.out.println(nums);
  }

}
