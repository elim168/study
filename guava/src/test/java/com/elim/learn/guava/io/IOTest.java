package com.elim.learn.guava.io;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.graph.Traverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * @author Elim
 * 19-4-20
 */
public class IOTest {

  @Test
  public void testFiles() throws Exception {
    Files.toByteArray(new File("pom.xml"));

//    Files.createParentDirs(new File("a/b/c/d/e/f"));


    Assert.assertEquals("xml", Files.getFileExtension("pom.xml"));
    Assert.assertEquals("pom", Files.getNameWithoutExtension("pom.xml"));

    Traverser<File> fileTraverser = Files.fileTraverser();
    File rootFile = new File("pom.xml").getAbsoluteFile().getParentFile();
//    fileTraverser.breadthFirst(rootFile).forEach(System.out::println);
    fileTraverser.depthFirstPreOrder(rootFile).forEach(System.out::println);

  }

  @Test
  public void testResources() throws Exception {
    List<String> lines = Resources.readLines(new URL("http://www.baidu.com"), Charsets.UTF_8);
    lines.forEach(line -> {
      System.out.println(line);
    });
  }

  @Test
  public void testByteSource() throws IOException {
    //与Source对应的还有Sink，比如ByteSink/CharSink等。Sink用于写，Source用于读。
    ByteSource byteSource = Files.asByteSource(new File("pom.xml"));
    InputStream is = byteSource.openStream();
    System.out.println(is.available());
  }

  @Test
  public void test() throws IOException {
    File file = new File("pom.xml");
    // Count distinct word occurrences in a file
    Multiset<String> wordOccurrences = HashMultiset.create(
            Splitter.on(CharMatcher.whitespace())
                    .trimResults()
                    .omitEmptyStrings()
                    .split(Files.asCharSource(file, Charsets.UTF_8).read()));
    wordOccurrences.elementSet().forEach(word -> {
      System.out.println(word + "-------------" + wordOccurrences.count(word));
    });

// SHA-1 a file
    HashCode hash = Files.asByteSource(file).hash(Hashing.sha256());
    System.out.println(hash.toString());

// Copy the data from a URL to a file
//    Resources.asByteSource(url).copyTo(Files.asByteSink(file));
  }

  @Test
  public void testCloser() throws IOException {
    Closer closer = Closer.create();
    try {
      InputStream in = closer.register(new FileInputStream("pom.xml"));
      OutputStream out = closer.register(new ByteArrayOutputStream());
      // do stuff with in and out
    } catch (Throwable e) { // must catch Throwable
      throw closer.rethrow(e);
    } finally {
      closer.close();
    }
  }

}
