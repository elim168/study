package com.elim.learn.spring.cloud.config.client.service;

import com.elim.learn.spring.cloud.config.client.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 2019/4/17
 */
@Service
public class HelloService {

  @Value("${info.foo:1}")
  private String infoFoo;

  @Autowired
  private TestProperties testProperties;

  @Autowired
  private TestService testService;

  @PostConstruct
  public void init() throws Exception {
    for (int i=0; i<2; i++) {
      System.out.println((i+1) + ".---------" + this.testService + "----------" + this.infoFoo + "-------" + this.testProperties);
//      TimeUnit.SECONDS.sleep(15);
    }
  }

}
