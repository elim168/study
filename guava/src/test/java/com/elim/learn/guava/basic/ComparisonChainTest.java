package com.elim.learn.guava.basic;

import com.google.common.collect.ComparisonChain;
import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Elim
 * 19-1-27
 */
public class ComparisonChainTest {

  @Test
  public void test() {
    Person person1 = new Person();
    person1.setFirstName("Alili");
    person1.setLastName("Blili");
    person1.setId(10L);

    Person person2 = new Person();
    person2.setFirstName("ABili");
    person2.setLastName("Blili");
    person2.setId(11L);

    List<Person> persons = Arrays.asList(person1, person2);
    Collections.sort(persons);
    System.out.println(persons);
  }

  @Data
  public static class Person implements Comparable<Person> {
    private Long id;
    private String firstName;
    private String lastName;

    @Override
    public int compareTo(Person o) {
      return ComparisonChain.start()
              .compare(this.firstName, o.firstName)
              .compare(this.lastName, o.lastName)
              .compare(this.id, o.id)
              .result();
    }
  }

}
