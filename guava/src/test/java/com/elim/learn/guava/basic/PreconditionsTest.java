package com.elim.learn.guava.basic;

import org.junit.Test;

import static com.google.common.base.Preconditions.*;

/**
 * @author Elim
 * 19-1-26
 */
public class PreconditionsTest {

  @Test
  public void test() {
    Integer abc = 123;
    checkArgument(abc > 100);//IllegalArgumentException
    checkNotNull(abc);//NullPointerException
    checkArgument(checkNotNull(abc) > 1);//checkNotNull返回当前值
    checkState(abc > 100);//IllegalStateException
    checkElementIndex(1, 10);
  }

}
