package com.elim.learn.spring.cloud.consul.server.controller;

import com.google.common.base.Preconditions;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Elim
 * 2019/1/28
 */
@RestController
@RequestMapping("hello")
public class HelloController {

  @GetMapping
  public String sayHello() {
    return "hello world! Date: " + LocalDateTime.now();
  }

  @GetMapping("{times}")
  @HystrixCommand(fallbackMethod = "sayHelloTimesFallback")
  public String sayHelloTimes(@PathVariable("times") int times) {
    Preconditions.checkArgument(times % 3 != 0);
    return "hello times: " + times + "----" + LocalDateTime.now();
  }

  public String sayHelloTimesFallback(int times) {
    return "hello times from callback: " + times + "----" + LocalDateTime.now();
  }

}
