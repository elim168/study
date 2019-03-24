package com.elim.learn.guava.primitives;

import com.google.common.primitives.Floats;
import org.junit.Test;

/**
 * @author Elim
 * 19-3-24
 */
public class FloatsTest {

  @Test
  public void test() {
    Floats.asList(1F, 2F);
    Floats.min(1F, 10F);
    Floats.indexOf(new float[]{1F, 2F}, 2F);
  }

}
