package com.elim.learn.guava.basic;

import com.google.common.base.MoreObjects;
import org.junit.Assert;
import org.junit.Test;


public class ObjectsTest {

  @Test
  public void firstNonNull() {
    Integer abc = 100;
    Integer bcd = null;
    Object firstNonNull = MoreObjects.firstNonNull(bcd, abc);
    Assert.assertEquals(100, firstNonNull);
  }
}
