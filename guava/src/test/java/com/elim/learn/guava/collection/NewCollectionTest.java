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
   * 双向关联的Map，底层是真正的Map
   */
  @Test
  public void testBiMap() {
    BiMap<String, Integer> map = HashBiMap.create();
    map.put("A", 1);
    map.put("B", 2);
    Assert.assertEquals(1, map.get("A").intValue());
    Assert.assertEquals("A", map.inverse().get(1));
  }

  /**
   * Table是为了解决Map<R, Map<C, V>>场景的，表示的是行、列、值三者之间的关系。
   * 其实现有基于HashMap的实现HashBasedTable、基于TreeMap的实现TreeBasedTable和
   * 底层使用数组存储的ArrayTable等。
   */
  @Test
  public void testTable() {
    Table<String, String, String> table = HashBasedTable.create();
    table.put("row1", "column1", "value1-1");
    table.put("row1", "column2", "value1-2");
    table.put("row1", "column3", "value1-3");
    table.put("row2", "column1", "value2-1");
    table.put("row2", "column2", "value2-2");
    table.put("row2", "column3", "value2-3");
    Assert.assertEquals(6, table.size());
    Assert.assertEquals("value1-1", table.get("row1", "column1"));
    Assert.assertNull(table.get("row3", "column1"));
    Assert.assertNull(table.get("row1", "column4"));
    Map<String, String> row1 = table.row("row1");
    Assert.assertEquals(3, row1.size());
    Assert.assertEquals("value1-1", row1.get("column1"));
    Map<String, String> column1 = table.column("column1");
    Assert.assertEquals(2, column1.size());
    Assert.assertEquals("value1-1", column1.get("row1"));

    //ArrayTable
    //ArrayTable的行和列是固定的，它有两种创建方式。
    ArrayTable<String, String, String> arrayTable = ArrayTable.create(table);
    Exception exception = null;
    try {
      //这里会抛出异常因为指定的行列组合原本不存在
      arrayTable.put("row3", "column1", "value3-1");
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull(exception);
    Assert.assertEquals("value1-1", arrayTable.get("row1", "column1"));
    arrayTable.put("row1", "column1", "value1-1-1");
    Assert.assertEquals("value1-1-1", arrayTable.get("row1", "column1"));
    //原来的Table是不受影响的
    Assert.assertEquals("value1-1", table.get("row1", "column1"));
  }

  @Test
  public void testClassToInstanceMap() {
    ClassToInstanceMap<Object> map = MutableClassToInstanceMap.create();
    map.putInstance(int.class, -1);
    map.putInstance(Integer.class, 1);
    map.putInstance(Long.class, 1000000000000000L);
    //getInstance可以避免类型转换
    Assert.assertEquals(-1, map.getInstance(int.class).intValue());
    Assert.assertEquals(1, map.getInstance(Integer.class).intValue());
    Assert.assertEquals(1000000000000000L, map.getInstance(Long.class).longValue());
    Assert.assertEquals(1000000000000000L, map.getInstance(Long.class).longValue());
  }

  @Test
  public void testRangeSet() {
    RangeSet<Integer> rangeSet = TreeRangeSet.create();
    rangeSet.add(Range.closed(1, 10));//[1,10]
    rangeSet.add(Range.closedOpen(11, 15));//[1,10],[11,15)
    System.out.println(rangeSet.asRanges());
    rangeSet.add(Range.closedOpen(15, 20));//[1,10],[11,20)
    System.out.println(rangeSet.asRanges());
    Assert.assertTrue(rangeSet.contains(9));
    Range<Integer> integerRange = rangeSet.rangeContaining(9);
    System.out.println(integerRange);


    //
    Range<Integer> range1 = Range.closedOpen(0, 10);//[0,10)
    Range<Integer> range2 = Range.closedOpen(10, 100);//[10,100)
    Range<Integer> range3 = Range.closed(100, 1000);//[100,1000]
    Assert.assertTrue(range1.contains(9));
    Assert.assertTrue(range2.contains(99));
    Assert.assertTrue(range3.contains(999));

    ImmutableRangeSet<Integer> immutableRangeSet = ImmutableRangeSet.<Integer>builder().add(range1).add(range2).add(range3).build();
    System.out.println(immutableRangeSet.asRanges());//[0,1000]

  }

  @Test
  public void testRangeMap() {
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
