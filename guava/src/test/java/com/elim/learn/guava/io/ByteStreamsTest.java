package com.elim.learn.guava.io;

import com.google.common.io.ByteStreams;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Elim
 * 19-4-7
 */
public class ByteStreamsTest {

  @Test
  public void test() throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ByteStreams.copy(new ByteArrayInputStream("abc".getBytes()), outputStream);
    Assert.assertEquals("abc", new String(outputStream.toByteArray()));
    Assert.assertEquals("abc", outputStream.toString());


    byte[] bytes = ByteStreams.toByteArray(new FileInputStream("/home/elim/.profile"));
    System.out.println(new String(bytes));
//    Files.newInputStream(Paths.get(""))
//    ByteStreams.readBytes()
    InputStream is = Files.newInputStream(Paths.get("pom.xml"));
    bytes = new byte[is.available()];
    ByteStreams.readFully(is, bytes);
    for (int i=0; i<5; i++) {
      System.out.println("****************************");
    }
    System.out.println(new String(bytes));


  }

}
