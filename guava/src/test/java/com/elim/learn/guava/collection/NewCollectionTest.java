package com.elim.learn.guava.collection;

import com.google.common.collect.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author Elim
 * 19-2-16
 */
public class NewCollectionTest {

  @Test
  public void testMultiset() {
    Multiset<Integer> set = HashMultiset.create();
    set.add(1);
    set.add(2);
    set.add(1);
    Assert.assertEquals(3, set.size());
    Assert.assertEquals(2, set.count(1));
    set.add(1, 10);
    Assert.assertEquals(12, set.count(1));
    set.remove(1, 10);
    Assert.assertEquals(2, set.count(1));

    //每个元素和元素出现次数的组合
    Set<Multiset.Entry<Integer>> entries = set.entrySet();
    entries.forEach(entry -> {
      Integer element = entry.getElement();
      int count = entry.getCount();
      if (element == 1) {
        Assert.assertEquals(2, count);
      } else if (element == 2) {
        Assert.assertEquals(1, count);
      } else {
        throw new IllegalStateException("Should Not Happen!");
      }
    });

    //去重了
    Assert.assertEquals(2, set.elementSet().size());

  }

  @Test
  public void testSortedMultiset() {
    SortedMultiset<Integer> sortedMultiset = TreeMultiset.create();
    for (int i=0; i<10; i++) {
      sortedMultiset.add(i);
    }
    Multiset.Entry<Integer> firstEntry = sortedMultiset.firstEntry();
    Assert.assertEquals(0, firstEntry.getElement().intValue());
    Multiset.Entry<Integer> lastEntry = sortedMultiset.lastEntry();
    Assert.assertEquals(9, lastEntry.getElement().intValue());

    //返回一个倒排序的视图
    SortedMultiset<Integer> descendingMultiset = sortedMultiset.descendingMultiset();
    Assert.assertEquals(9, descendingMultiset.firstEntry().getElement().intValue());
    Assert.assertEquals(0, sortedMultiset.firstEntry().getElement().intValue());

    Assert.assertEquals(10, descendingMultiset.size());
    //拿出了最后一个元素
    Assert.assertEquals(0, descendingMultiset.pollLastEntry().getElement().intValue());
    Assert.assertEquals(9, descendingMultiset.size());

    //因为descendingMultiset.pollLastEntry()操作已经拿掉一个元素了
    Assert.assertEquals(9, sortedMultiset.size());

    //取元素5以前的所有元素，且包括元素5。
    Assert.assertEquals(5, sortedMultiset.headMultiset(5, BoundType.CLOSED).size());
    Assert.assertEquals(4, sortedMultiset.headMultiset(5, BoundType.OPEN).size());

    //7、8、9
    Assert.assertEquals(3, descendingMultiset.headMultiset(7, BoundType.CLOSED).size());

  }

  /**
   * Multimap又分ListMultimap和SetMultimap，ListMultimap的Value是List结构，即允许重复的Key-Value
   * 组合，而SetMultimap的Value是Set结构，即不允许重复的Key-Value结构。
   */
  @Test
  public void testMultiMap() {
    //Value对应的集合类型默认是Set
    Multimap<String, Integer> setMultimap = HashMultimap.create();
    setMultimap.put("A", 1);
    setMultimap.put("B", 3);
    setMultimap.put("A", 2);
    setMultimap.put("B", 2);
    setMultimap.put("B", 2);

    //size是所有key-value的组合
    Assert.assertEquals(4, setMultimap.size());
    Assert.assertEquals(4, setMultimap.values().size());
    Collection<Integer> aValues = setMultimap.get("A");
    Assert.assertEquals(2, aValues.size());

    Assert.assertTrue(setMultimap.containsKey("A"));
    Assert.assertTrue(setMultimap.containsEntry("A", 1));
    Assert.assertTrue(setMultimap.containsValue(1));
    setMultimap.get("A").add(3);
    Assert.assertEquals(5, setMultimap.size());

    //Value结构是List
    ListMultimap<String, Integer> listMultimap = ArrayListMultimap.create();
    listMultimap.put("A", 1);
    listMultimap.put("A", 1);
    listMultimap.put("A", 1);
    Assert.assertEquals(3, listMultimap.size());
    Assert.assertEquals(3, listMultimap.get("A").size());
    Map<String, Collection<Integer>> map = listMultimap.asMap();
    Collection<Integer> aValueColl = map.get("A");
    Assert.assertTrue(aValueColl instanceof List);

    //通过MultimapBuilder进行构造，其可以构造出非常灵活的Multimap结构

    //1.底层通过TreeMap保存Key，Value是Set结构
    SetMultimap<Comparable, Object> buildMap1 = MultimapBuilder.treeKeys().hashSetValues().build();
    buildMap1.put("A", 1);
    buildMap1.put("B", 2);
    buildMap1.put("C", 3);
    buildMap1.put("C", 3);
    Assert.assertEquals(3, buildMap1.size());

    Map<Comparable, Collection<Object>> buildMap1AsMap = buildMap1.asMap();
    Assert.assertTrue(buildMap1AsMap instanceof NavigableMap);

    //2.底层通过HashMap保存Key，Value是List结构
    ListMultimap<String, Integer> buildMap2 = MultimapBuilder.hashKeys().arrayListValues().build();
    buildMap2.put("A", 1);
    buildMap2.put("A", 1);
    buildMap2.put("B", 2);
    Assert.assertEquals(3, buildMap2.size());

  }

  /**
   * 双向关联的Map
   */
  @Test
  public void testBiMap() {
    BiMap<String, Integer> map = HashBiMap.create();
    map.put("A", 1);
    map.put("B", 2);
    Assert.assertEquals(1, map.get("A").intValue());
    Assert.assertEquals("A", map.inverse().get(1));
  }

}
