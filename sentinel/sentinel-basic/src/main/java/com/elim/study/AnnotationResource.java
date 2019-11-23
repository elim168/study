package com.elim.study;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 19-11-23
 */
public class AnnotationResource {

  @SentinelResource(value = "hello")
  public void sayHello() {
    System.out.println("Hello World!");
    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
