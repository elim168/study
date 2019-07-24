package com.elim.study.dubbo;

import com.elim.study.dubbo.model.Person;
import com.google.common.collect.ImmutableSet;
import org.apache.dubbo.common.serialize.support.SerializationOptimizer;

import java.util.Collection;

/**
 * @author Elim
 * 19-7-24
 */
public class MySerializationOptimizer implements SerializationOptimizer {
  @Override
  public Collection<Class> getSerializableClasses() {
    return ImmutableSet.of(Person.class);
  }
}
