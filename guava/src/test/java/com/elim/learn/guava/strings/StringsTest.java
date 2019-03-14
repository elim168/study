package com.elim.learn.guava.strings;

import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Elim
 * 19-3-14
 */
public class StringsTest {

  @Test
  public void test() {
    String result = Strings.repeat("A", 6);
    Assert.assertEquals("AAAAAA", result);

    result = Strings.commonPrefix("abcdefg", "abcde123");//共同的前缀
    Assert.assertEquals("abcde", result);

    result = Strings.commonSuffix("aaaa123a", "bbbbb12a3a");//共同的后缀
    Assert.assertEquals("3a", result);

    Assert.assertNull(Strings.emptyToNull(""));
    Assert.assertEquals("", Strings.nullToEmpty(null));

    Assert.assertTrue(Strings.isNullOrEmpty(""));
    Assert.assertFalse(Strings.isNullOrEmpty("  "));

    result = Strings.padEnd("abc", 6, '*');
    Assert.assertEquals("abc***", result);

    result = Strings.padStart("abc", 6, '*');
    Assert.assertEquals("***abc", result);
  }

}
