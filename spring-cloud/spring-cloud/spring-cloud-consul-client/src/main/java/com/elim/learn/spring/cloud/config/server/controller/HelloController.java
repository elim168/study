package com.elim.learn.spring.cloud.config.server.controller;

import com.elim.learn.spring.cloud.config.server.service.HelloService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Elim
 * 2019/1/28
 */
@RestController
public class HelloController {

  @Autowired
  private HelloService helloService;

  @GetMapping("hello")
  public String hello() {
    return this.helloService.hello();
  }

  @GetMapping("hello2")
  @HystrixCommand(fallbackMethod = "hello2Fallback")
  public String hello2() {
    return this.helloService.hello2();
  }

  public String hello2Fallback() {
    return "hello2 fallback result.";
  }

}
