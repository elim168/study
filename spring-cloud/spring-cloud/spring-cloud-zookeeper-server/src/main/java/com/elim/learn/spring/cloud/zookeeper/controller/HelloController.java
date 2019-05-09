package com.elim.learn.spring.cloud.zookeeper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Elim
 * 2019/5/9
 */
@RestController
@RequestMapping("hello")
public class HelloController {

  @GetMapping
  public String hello() {
    return "hello, now is " + new Date();
  }

}
