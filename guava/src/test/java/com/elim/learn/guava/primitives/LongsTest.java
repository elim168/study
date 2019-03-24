package com.elim.learn.guava.primitives;

import com.google.common.primitives.Longs;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Elim
 * 19-3-24
 */
public class LongsTest {

  @Test
  public void test() {
    Longs.asList(1, 2, 3);
    Longs.compare(1, 2);
    Longs.constrainToRange(10, 20, 30);//返回最接近指定范围的数字
    Longs.contains(new long[]{1, 2, 3}, 1);
    Longs.join(",", new long[]{1, 2, 3});
    Assert.assertEquals(1L, Longs.fromByteArray(new byte[]{0, 0, 0, 0, 0, 0, 0, 1}));
    Assert.assertEquals(257L, Longs.fromByteArray(new byte[]{0, 0, 0, 0, 0, 0, 1, 1}));
    long longFromBytes = Longs.fromBytes((byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1);
    Assert.assertEquals(1L, longFromBytes);
  }

}
