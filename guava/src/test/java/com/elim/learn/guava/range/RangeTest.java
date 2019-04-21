package com.elim.learn.guava.range;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Elim
 * 19-4-7
 */
public class RangeTest {

  @Test
  public void test() {
    List<Double> scores = ImmutableList.of(1.2, 2.3, 3.6, 4.5, 8.8);
    Iterable<Double> lessThan3dot8 = Iterables.filter(scores, Range.lessThan(3.8));
    lessThan3dot8.forEach(System.out::println);


  }

  @Test
  public void test2() {
    List<Integer> ints = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<Integer> range5to8 = ints.stream().filter(Range.closed(5, 8)).collect(Collectors.toList());
    Assert.assertEquals(4, range5to8.size());

    Assert.assertEquals(5, range5to8.get(0).intValue());
    Assert.assertEquals(6, range5to8.get(1).intValue());
    Assert.assertEquals(7, range5to8.get(2).intValue());
    Assert.assertEquals(8, range5to8.get(3).intValue());


    Range.lessThan(10);//x<10
    Range.closed(10, 20);//10<=x<=20
    Range.closedOpen(10, 20);//10<=x<20
    Range.all();//所有
    Range.atLeast(10);//x>=10
    Range.atMost(10);//x<=10
    Range.downTo(10, BoundType.CLOSED);//x>=10
    Range.downTo(10, BoundType.OPEN);//x>10
    Range.greaterThan(10);//x>10
    Range.open(10, 20);//10<x<20


    Range<Integer> range = Range.range(10, BoundType.OPEN, 20, BoundType.CLOSED);
    Assert.assertTrue(range.contains(11));
    Assert.assertFalse(range.contains(10));

    Assert.assertTrue(range.hasLowerBound());
    Assert.assertTrue(range.hasUpperBound());
    Assert.assertEquals(BoundType.OPEN, range.lowerBoundType());
    Assert.assertEquals(10, range.lowerEndpoint().intValue());


    Range<Integer> intersection = Range.open(1, 10).intersection(Range.open(8, 12));
    Assert.assertTrue(intersection.equals(Range.open(8, 10)));


    ContiguousSet<Integer> integers = ContiguousSet.create(Range.closed(1, 10), DiscreteDomain.integers());
    Assert.assertEquals(10, integers.size());

  }

  @Test
  public void test3() {
    TreeRangeSet<Comparable<?>> rangeSet = TreeRangeSet.create();
    rangeSet.add(Range.closedOpen(1, 10));
    rangeSet.add(Range.closedOpen(10, 20));
    System.out.println(rangeSet);//[1..20)
    RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
    rangeMap.put(Range.closedOpen(0, 10), "A");
    rangeMap.put(Range.closedOpen(10, 80), "B");
    rangeMap.put(Range.closed(80, 100), "C");

    for (int i=0; i<10; i++) {
      Assert.assertEquals("A", rangeMap.get(i));
    }
    for (int i=10; i<80; i++) {
      Assert.assertEquals("B", rangeMap.get(i));
    }
    for (int i=80; i<=100; i++) {
      Assert.assertEquals("C", rangeMap.get(i));
    }
  }

}
