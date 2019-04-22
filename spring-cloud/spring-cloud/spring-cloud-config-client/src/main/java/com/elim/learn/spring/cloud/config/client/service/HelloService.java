package com.elim.learn.spring.cloud.config.client.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Elim
 * 2019/4/17
 */
@Service
public class HelloService {

  @Value("${info.foo:1}")
  private String infoFoo;

  @PostConstruct
  public void init() {
    for (int i=0; i<10; i++) {
      System.out.println((i+1) + ".-------------------" + this.infoFoo);
    }
  }

}
