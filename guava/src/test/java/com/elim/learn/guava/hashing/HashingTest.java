package com.elim.learn.guava.hashing;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.junit.Test;

/**
 * @author Elim
 * 19-4-20
 */
public class HashingTest {

  @Test
  public void test() {
    HashFunction hf = Hashing.goodFastHash(32);
    HashCode hc = hf.newHasher()
            .putLong(100L)
            .putString("ZhangSan", Charsets.UTF_8)
            .hash();
    System.out.println(hc);
    System.out.println(hc.hashCode());


    hc = Hashing.sha256().newHasher().putString("ZhangSan", Charsets.UTF_8).hash();
    System.out.println(hc.toString());//b250ab4c8f91c1271e36c9d98a7351e817f90b93198c43d9edf14690f94156b4
  }

}
