package com.elim.learn.guava.primitives;

import com.google.common.primitives.Booleans;
import org.junit.Test;

/**
 * @author Elim
 * 19-3-24
 */
public class BooleansTest {

  @Test
  public void test() {
    Booleans.asList(true, false, true, false);
    Booleans.compare(true, false);
    Booleans.indexOf(new boolean[]{true, false, true, false}, false);
    Booleans.indexOf(new boolean[]{true, false, true, false}, false);
  }

}
