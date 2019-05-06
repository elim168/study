package com.elim.learn.spring.cloud.config.client;

import javax.annotation.PostConstruct;

/**
 * @author Elim
 * 2019/4/24
 */
public class TestService {

  @PostConstruct
  public void init() {
    for (int i=0; i<10; i++) {
      System.out.println("*********************" + this);
    }
  }

}
