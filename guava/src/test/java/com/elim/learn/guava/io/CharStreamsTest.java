package com.elim.learn.guava.io;

import com.google.common.io.CharStreams;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Elim
 * 19-4-20
 */
public class CharStreamsTest {

  @Test
  public void test() throws Exception {
    String string = CharStreams.toString(Files.newBufferedReader(Paths.get("pom.xml")));
    System.out.println(string);

    List<String> lines = CharStreams.readLines(Files.newBufferedReader(Paths.get("pom.xml")));
    int num = 0;
    for (String line : lines) {
      System.out.println(++num + ". " + line);
    }


    byte[] bytes = com.google.common.io.Files.toByteArray(new File("pom.xml"));
    System.out.println(bytes.length);
  }

}
