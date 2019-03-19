package com.elim.learn.guava.primitives;

import com.google.common.primitives.Shorts;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Elim
 * 19-3-19
 */
public class ShortsTest {

  @Test
  public void test() {
    List<Short> shorts = Shorts.asList((short) 1, (short) 2, (short) 3);
    Assert.assertEquals(3, shorts.size());

//    Shorts.max()
//    Shorts.indexOf()

  }

}
