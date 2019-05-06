package com.elim.learn.spring.cloud.client.service;

import brave.Span;
import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;

/**
 * @author Elim
 * 2019/5/5
 */
@Service
public class SleuthLocalService2 {

  @Autowired
  private Tracer tracer;

  @NewSpan
  public void method1() {
    Span span = this.tracer.currentSpan();
    span.tag("tagA", "123");
    span.annotate("finish...");
  }

}
