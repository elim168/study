package com.elim.learn.guava.strings;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Elim
 * 19-3-14
 */
public class CaseFormatTest {

  @Test
  public void test() {
    Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN);
    Assert.assertEquals("abc-def", converter.convert("abcDef"));

    String abcDef = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "abc_def");
    Assert.assertEquals("abcDef", abcDef);
  }


}
