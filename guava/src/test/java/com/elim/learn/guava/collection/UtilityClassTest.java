package com.elim.learn.guava.collection;

import com.google.common.base.Joiner;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author Elim
 * 19-2-17
 */
public class UtilityClassTest {

  @Test
  public void test() {
    List<Integer> list = Lists.newArrayList(3, 2, 1);
    //所有的排列组合
    Collection<List<Integer>> lists = Collections2.orderedPermutations(list);
    System.out.println(lists);
    System.out.println(lists.size());
    for (List<Integer> element : lists) {
      System.out.println(element);
    }

    Assert.assertEquals(2, Collections2.filter(list, ele -> ele > 1).size());
    System.out.println("--------------------------------------");
    Collections2.permutations(list).forEach(System.out::println);
  }

  @Test
  public void testIterators() {
    List<Integer> list1 = Lists.newArrayList(1, 2, 3);
    List<Integer> list2 = Lists.newArrayList(11, 12, 13);
    for (Iterator iter = Iterators.concat(list1.iterator(), list2.iterator()); iter.hasNext(); ) {
      System.out.println(iter.next());
    }

    System.out.println(Collections.frequency(list1, 2));
    System.out.println(Iterators.frequency(list1.iterator(), 2));

    List<Integer> list3 = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    for (Iterator<List<Integer>> iter = Iterators.partition(list3.iterator(), 4); iter.hasNext(); ) {
      System.out.println(iter.next());
    }

    Assert.assertEquals(10, Iterators.getLast(list3.iterator(), 0).intValue());
    Assert.assertEquals(1, Iterators.getNext(list3.iterator(), 0).intValue());

    Iterator<Integer> iterator3 = list3.iterator();
    Assert.assertEquals(4, Iterators.get(iterator3, 3).intValue());
//    Assert.assertEquals(4, Iterators.get(iterator3, 3).intValue());//Error，会接着后面取3个，应该是8
    Iterators.limit(list3.iterator(), 5).forEachRemaining(System.out::println);
    Iterators.size(list3.iterator());


  }

  @Test
  public void testIterables() {
    List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
    Assert.assertEquals(5, Iterables.size(list));

    Assert.assertEquals(10, Iterables.size(Iterables.concat(list, list)));
    Assert.assertEquals(1, Iterables.getFirst(list, 0).intValue());

    Iterables.limit(list, 3).forEach(System.out::println);//1,2,3

    Iterables.addAll(list, list);
    Assert.assertEquals(10, list.size());
    FluentIterable.of(1, 2, 3, 4, 5).toList();
    Assert.assertTrue(FluentIterable.from(list).join(Joiner.on(",")).equals("1,2,3,4,5,1,2,3,4,5"));
  }

  @Test
  public void testLists() {
    List<Integer> list1 = Ints.asList(1, 2, 3, 4, 5);
    Assert.assertEquals(5, Lists.reverse(list1).get(0).intValue());
    Assert.assertEquals(1, list1.get(0).intValue());

    List<List<Integer>> partitionedLists = Lists.partition(list1, 2);
    Assert.assertEquals(3, partitionedLists.size());

    LinkedList<Integer> linkedList = Lists.newLinkedList();
    linkedList.add(1);
    linkedList.add(2);
    linkedList.add(3);
    linkedList.add(4);
  }

  @Test
  public void testSets() {
    Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4);
    Set<Integer> set2 = Sets.newHashSet(3, 4, 5, 6, 7, 8);
    Sets.SetView<Integer> setView = Sets.union(set1, set2);//unmodifiable
    Assert.assertTrue(setView instanceof Set);
    Assert.assertEquals(8, setView.size());

    Sets.SetView<Integer> intersectionResult = Sets.intersection(set1, set2);
    Assert.assertEquals(2, intersectionResult.size());
    Assert.assertTrue(intersectionResult.contains(3));
    Assert.assertTrue(intersectionResult.contains(4));

    Sets.SetView<Integer> differenceResult = Sets.difference(set1, set2);//set1 difference set2
    Assert.assertEquals(2, differenceResult.size());
    Assert.assertTrue(differenceResult.contains(1));
    Assert.assertTrue(differenceResult.contains(2));

    Sets.SetView<Integer> symmetricDifferenceResult = Sets.symmetricDifference(set1, set2);//all besides both contained.
    Assert.assertEquals(6, symmetricDifferenceResult.size());
    Assert.assertTrue(symmetricDifferenceResult.contains(1));
    Assert.assertTrue(symmetricDifferenceResult.contains(2));
    Assert.assertTrue(symmetricDifferenceResult.contains(5));
    Assert.assertTrue(symmetricDifferenceResult.contains(6));
    Assert.assertTrue(symmetricDifferenceResult.contains(7));
    Assert.assertTrue(symmetricDifferenceResult.contains(8));

    Sets.cartesianProduct(set1, set2).forEach(System.out::println);//每个set中取出一个的所有组合

    Sets.powerSet(set1).forEach(System.out::println);//所有的子Set的组合

    Sets.newConcurrentHashSet();
    Sets.newCopyOnWriteArraySet();
  }

  @Test
  public void testMaps() {
    Set<String> set = Sets.newHashSet("A", "B", "C", "D");
    ImmutableMap<String, String> map = Maps.uniqueIndex(set, String::toLowerCase);
    Assert.assertEquals("A", map.get("a"));
    Assert.assertEquals("B", map.get("b"));

    Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
    Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 4, "d", 5);
    MapDifference<String, Integer> diff = Maps.difference(left, right);//两个Map之间的区别

    diff.entriesInCommon(); // {"b" => 2} //都有的，且Key和Value都一样的
    diff.entriesDiffering(); // {"c" => (3, 4)} //都有的Key，但是Value不一样的
    diff.entriesOnlyOnLeft(); // {"a" => 1} //只有左边存在的Key
    diff.entriesOnlyOnRight(); // {"d" => 5}  //只有右边存在的Key

    Maps.newConcurrentMap();
    Maps.newTreeMap();

    BiMap<String, Integer> biMap = HashBiMap.create();
    biMap.put("A", 1);
    BiMap<String, Integer> synchronizedBiMap = Maps.synchronizedBiMap(biMap);
    synchronizedBiMap.put("B", 2);
  }

  @Test
  public void testMultisets() {
    Multiset<Integer> multiset1 = HashMultiset.create();
    multiset1.add(1, 3);
    multiset1.add(2);
    Multiset<Integer> multiset2 = HashMultiset.create();
    multiset2.add(1, 2);
    multiset2.add(2);

    //只要唯一的元素都存在，containsAll就返回true，它不比较每个元素出现的次数
    Assert.assertTrue(multiset2.containsAll(multiset1));
    //每个元素在multiset1中出现的次数都大于或等于在multiset2中出现的次数
    Assert.assertTrue(Multisets.containsOccurrences(multiset1, multiset2));

    Multiset<Integer> multiset1Copy = ImmutableMultiset.copyOf(multiset1);

    //从multiset1中移除所有的multiset2中的元素，跟数量有关，所以下面的操作进行后multiset1中只剩元素1一个，因为multiset2中有两个1,原本
    //multiset1中有3个1。
    Multisets.removeOccurrences(multiset1, multiset2);
    Assert.assertEquals(1, multiset1.size());
    Assert.assertEquals(1, multiset1.count(1));

    multiset1 = HashMultiset.create(multiset1Copy);
    Assert.assertTrue(Multisets.retainOccurrences(multiset1, multiset2));
    Assert.assertTrue(multiset1.equals(multiset2));


    multiset1 = HashMultiset.create(multiset1Copy);
    //它和retainOccurrences的区别是retainOccurrences会从multiset1中删除元素，而intersection不会删除元素，只是返回拥有相同元素的视图
    Assert.assertTrue(Multisets.intersection(multiset1, multiset2).equals(multiset2));
    Assert.assertEquals(3, multiset1.count(1));

    Multiset<String> multiset = HashMultiset.create();
    multiset.add("a", 3);
    multiset.add("b", 5);
    multiset.add("c", 1);

    ImmutableMultiset<String> highestCountFirst = Multisets.copyHighestCountFirst(multiset);
    // highestCountFirst, like its entrySet and elementSet, iterates over the elements in order {"b", "a", "c"}
  }

  @Test
  public void testMultimaps() {
    ImmutableSet<String> immutableSet = ImmutableSet.of("abc", "bcd", "abcd");
    ImmutableListMultimap<Integer, String> multimap = Multimaps.index(immutableSet, String::length);
    Assert.assertEquals(3, multimap.size());
    Assert.assertEquals(2, multimap.keySet().size());
    Assert.assertEquals(2, multimap.get(3).size());


    ArrayListMultimap<String, Integer> multimap2 = ArrayListMultimap.create();
    multimap2.putAll("b", Ints.asList(2, 4, 6));
    multimap2.putAll("a", Ints.asList(4, 2, 1));
    multimap2.putAll("c", Ints.asList(2, 5, 3));

    //把Key和Value进行互转
    HashMultimap<Integer, String> invertedMultimap = Multimaps.invertFrom(multimap2, HashMultimap.create());

    Assert.assertTrue(invertedMultimap.containsKey(1));
    Assert.assertTrue(invertedMultimap.containsKey(2));
    Assert.assertEquals(3, invertedMultimap.get(2).size());//a,b,c
    //ImmutableMultimap可以直接调用其inverse()方法
    ImmutableMultimap.copyOf(invertedMultimap).inverse();


    Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
    //把Map转换为SetMultimap
    SetMultimap<String, Integer> setMultimap = Multimaps.forMap(map);
    TreeMultimap<Integer, String> treeMultimap = Multimaps.invertFrom(setMultimap, TreeMultimap.create());
    Assert.assertTrue(treeMultimap.containsKey(1));
    Assert.assertEquals(2, treeMultimap.get(1).size());//a,b
  }

  @Test
  public void testTables() {
    Table<String, String, String> table = HashBasedTable.create();
    Table<String, String, String> synchronizedTable = Tables.synchronizedTable(table);
    synchronizedTable.put("row1", "column1", "11");
    synchronizedTable.put("row1", "column2", "12");
    synchronizedTable.put("row2", "column1", "21");
    synchronizedTable.put("row2", "column2", "22");
    //行列转换
    Table<String, String, String> transposedTable = Tables.transpose(table);
    Map<String, String> column1 = transposedTable.row("column1");
    Assert.assertEquals(2, column1.size());
    Assert.assertEquals("11", column1.get("row1"));
    Assert.assertEquals("21", column1.get("row2"));

  }

}
