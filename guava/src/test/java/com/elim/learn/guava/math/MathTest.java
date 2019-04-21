package com.elim.learn.guava.math;

import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * @author Elim
 * 19-4-21
 */
public class MathTest {

  @Test
  public void test() {
    int i = LongMath.log2(64, RoundingMode.FLOOR);
    Assert.assertEquals(6, i);

    i = IntMath.checkedMultiply(10, 100);//确保结果不会溢出
    Assert.assertEquals(1000, i);
    IntMath.checkedAdd(10, 100);

    long quotient = LongMath.divide(3 * 10, 3, RoundingMode.UNNECESSARY);
    Assert.assertEquals(10, quotient);

    BigInteger nearestInteger = DoubleMath.roundToBigInteger(8.88, RoundingMode.HALF_EVEN);
    Assert.assertEquals(9, nearestInteger.intValue());

    nearestInteger = DoubleMath.roundToBigInteger(8.5, RoundingMode.HALF_EVEN);
    Assert.assertEquals(8, nearestInteger.intValue());


    Assert.assertEquals(5, IntMath.gcd(10, 25));//最大公约数


    Assert.assertTrue(IntMath.isPowerOfTwo(64));
    Assert.assertFalse(IntMath.isPowerOfTwo(128));

    Assert.assertEquals(100, IntMath.pow(10, 2));

  }

}
