package com.elim.learn.guava.primitives;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Elim
 * 19-3-16
 */
public class BytesTest {

  @Test
  public void test() {
    List<Byte> bytes = Bytes.asList("abc".getBytes(Charsets.UTF_8));
    Assert.assertEquals(3, bytes.size());
    bytes.forEach(System.out::println);//97,98,99


    byte[] byteArray = Bytes.concat("a".getBytes(), "b".getBytes(), "c".getBytes());//把多个Byte数组组合成一个新的Byte数组
    Bytes.asList(byteArray).forEach(System.out::println);//97,98,99
    Bytes.reverse(byteArray);
    Bytes.asList(byteArray).forEach(System.out::println);//99,98,97

    Assert.assertTrue(Bytes.contains(byteArray, (byte)98));

    Assert.assertEquals(1, Bytes.indexOf(byteArray, (byte) 98));

    byte[] bytes1 = Bytes.toArray(ImmutableList.of(1, 2, 3, 4, 5, 6));

    System.out.println(Arrays.toString(bytes1));//1,2,3,4,5,6

    System.out.println(Integer.toBinaryString(10));//1010

  }

  @Test
  public void testSignedBytes() {
    System.out.println(SignedBytes.join(",", "中文将".getBytes()));//-28,-72,-83,-26,-106,-121,-27,-80,-122
    System.out.println(SignedBytes.join(",", "AA".getBytes()));//65,65
  }

  @Test
  public void testUnSignedBytes() {
    System.out.println(UnsignedBytes.toString((byte)65));
    System.out.println(UnsignedBytes.join(",", "中文将".getBytes()));//228,184,173,230,150,135,229,176,134
  }

}
