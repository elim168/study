package com.elim.learn.guava.primitives;

import com.google.common.primitives.Doubles;
import org.junit.Test;

/**
 * @author Elim
 * 19-3-24
 */
public class DoublesTest {

  @Test
  public void test() {
    Doubles.asList(1.2, 2.3, 3.4);
    Doubles.compare(1.2, 2.3);
    Doubles.min(1.2, 2.1);
  }

}
