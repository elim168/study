package com.elim.learn.guava.primitives;

import com.google.common.primitives.Chars;
import org.junit.Test;

/**
 * @author Elim
 * 19-3-24
 */
public class CharsTest {

  @Test
  public void test() {
    Chars.asList('A', 'B', 'C');
    Chars.contains(new char[]{'A', 'B', 'C'}, 'D');
    Chars.min('A', 'D');

    Chars.toArray(Chars.asList('A', 'B'));

    Chars.concat(new char[]{'A', 'B'}, new char[]{'C', 'D'});


  }

}
