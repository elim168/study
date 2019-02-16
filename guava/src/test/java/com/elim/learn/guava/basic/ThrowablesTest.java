package com.elim.learn.guava.basic;

import com.google.common.base.Throwables;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Elim
 * 19-2-14
 */
public class ThrowablesTest {

  @Test
  public void test() {
    Exception exception = null;
    try {
      int i=0;
      i = 10/0;
    } catch (Exception e) {
      try {
        //当Throwable是RuntimeException/Error或指定的IOException时将继续抛出异常
        Throwables.propagateIfPossible(e, IOException.class);
      } catch (Exception e1) {
        exception = e1;
      }
    }
    Assert.assertNotNull(exception);
    Assert.assertTrue(exception instanceof ArithmeticException);
  }

  @Test
  public void test2() {
    Exception exception = null;
    try {
      int i=0;
      i = 10/0;
    } catch (Exception e) {
      try {
        //当Throwable是ArithmeticException类型时将继续抛出异常
        Throwables.throwIfInstanceOf(e, ArithmeticException.class);
      } catch (Exception e1) {
        exception = e1;
      }
    }
    Assert.assertNotNull(exception);
    Assert.assertTrue(exception instanceof ArithmeticException);
  }

  @Test
  public void test3() {
    Exception exception = null;
    try {
      int i=0;
      i = 10/0;
    } catch (Exception e) {
      try {
        //当Throwable是非受检异常时将继续抛出异常，即RuntimeException或Error。
        Throwables.throwIfUnchecked(e);
      } catch (Exception e1) {
        exception = e1;
      }
    }
    Assert.assertNotNull(exception);
    Assert.assertTrue(exception instanceof ArithmeticException);
  }

}
