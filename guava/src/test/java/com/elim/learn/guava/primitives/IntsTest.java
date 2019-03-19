package com.elim.learn.guava.primitives;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedInts;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Elim
 * 19-3-19
 */
public class IntsTest {

  @Test
  public void test() {
    Ints.asList(1, 2, 3);
    Assert.assertTrue(Ints.compare(1, 2) < 0);
    Assert.assertEquals(4, Ints.concat(new int[]{1, 2}, new int[]{3, 4}).length);
    Assert.assertTrue(Ints.contains(new int[]{1, 2, 3}, 3));
    Assert.assertEquals(2, Ints.indexOf(new int[]{1, 2, 3}, 3));
    Assert.assertEquals(3, Ints.indexOf(new int[]{1, 2, 3, 4, 1, 3, 4}, new int[]{4, 1, 3}));
    Assert.assertEquals("1|2|3", Ints.join("|", new int[]{1, 2, 3}));
    Ints.max(new int[]{1, 3, 6});

    Ints.reverse(new int[0]);
    Ints.toArray(ImmutableList.of(1, 2, 3));
    Ints.checkedCast(100L);
    Ints.constrainToRange(10, 20, 30);//20，返回指定范围（20-30）内最近10的数
    Ints.lastIndexOf(new int[0], 10);
    Assert.assertNull(Ints.tryParse("ABC"));
    Assert.assertNotNull(Ints.tryParse("-123"));

    Ints.sortDescending(new int[10]);


    UnsignedInts.checkedCast(10L);
    UnsignedInts.decode("#18Acde");
    UnsignedInts.decode("0xAA");

    UnsignedInts.max(new int[10]);

    UnsignedInteger.valueOf("888");
  }

}
