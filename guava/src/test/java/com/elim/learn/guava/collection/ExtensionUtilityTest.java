package com.elim.learn.guava.collection;

import com.google.common.collect.*;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * @author Elim
 * 19-2-24
 */
public class ExtensionUtilityTest {

  @Test
  public void testForwarding() {
    List<Integer> list = Lists.newArrayList();
    List<Integer> decoratedList = new AddLoggingList<>(list);
    decoratedList.add(1);
    decoratedList.add(2);
    Assert.assertEquals(2, decoratedList.size());

    Multimap<String, Integer> multimap = HashMultimap.create();
    Multimap<String, Integer> decoratedMultimap = new AddLoggingMultimap<>(multimap);
    decoratedMultimap.put("A", 1);
    decoratedMultimap.put("B", 2);
    decoratedMultimap.put("C", 3);
    Assert.assertEquals(3, multimap.size());
  }

  /**
   * ForwardingXXX可以允许我们重写集合类的某些方法，不重写的方法默认都转发给delegate调用
   * @param <E>
   */
  @RequiredArgsConstructor
  class AddLoggingList<E> extends ForwardingList<E> {

    private final List<E> delegate;

    @Override
    protected List<E> delegate() {
      return delegate;
    }

    @Override
    public boolean add(E element) {
      System.out.println("Add Element " + element);
      return super.add(element);
    }
  }

  @RequiredArgsConstructor
  class AddLoggingMultimap<K, V> extends ForwardingMultimap<K, V> {

    private final Multimap<K, V> delegate;

    @Override
    protected Multimap<K, V> delegate() {
      return delegate;
    }

    @Override
    public boolean put(K key, V value) {
      System.out.println("Add A Element key=" + key + ",value=" + value);
      return super.put(key, value);
    }
  }

  /**
   * peek()可以获取Iterator的当前元素（对应于hasNext()的当前元素）
   */
  @Test
  public void testPeekingIterator() {
    List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
    PeekingIterator<Integer> peekingIterator = Iterators.peekingIterator(list.iterator());
    int i = 0;
    while (peekingIterator.hasNext()) {
      Integer peekVal = peekingIterator.peek();
      Assert.assertEquals(list.get(i), peekVal);
      Assert.assertEquals(list.get(i), peekingIterator.peek());
      Integer nextVal = peekingIterator.next();
      Assert.assertEquals(list.get(i), nextVal);
      i++;
    }
  }

  @Test
  public void testAbstractIterator() {
    Iterator<String> iterator = Iterators.forArray("A", "B", "C", null, null, "D");
    SkipNullIterator<String> skipNullIterator = new SkipNullIterator<>(iterator);

    List<String> list = Lists.newArrayList();
    while (skipNullIterator.hasNext()) {
      list.add(skipNullIterator.next());
    }
    Assert.assertEquals(4, list.size());
  }

  /**
   * 调用AbstractIterator的next()返回的是computeNext()的结果
   * @param <T>
   */
  @RequiredArgsConstructor
  class SkipNullIterator<T> extends AbstractIterator<T> {

    private final Iterator<T> delegate;

    @Override
    protected T computeNext() {
      while (this.delegate.hasNext()) {
        T result = this.delegate.next();
        if (result != null) {
          return result;
        }
      }
      //没有元素了需要调用endOfData()
      return super.endOfData();
    }
  }

  @Test
  public void testAbstractSequentialIterator() {
    PowerIterator iterator = new PowerIterator(1);
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }

  class PowerIterator extends AbstractSequentialIterator<Integer> {

    /**
     * Creates a new iterator with the given first element, or, if {@code firstOrNull} is null,
     * creates a new empty iterator.
     *
     * @param firstOrNull
     */
    public PowerIterator(@Nullable Integer firstOrNull) {
      super(firstOrNull);
    }

    @Nullable
    @Override
    protected Integer computeNext(Integer previous) {
      //基于前一个数返回当前值，当返回值为null时Iterator就结束了。
      Integer result = null;
      if (previous == null || previous > 30) {
        return null;
      } else {
        return 2 * previous;
      }
    }
  }

}
