package com.elim.learn.spring.cloud.zookeeper.controller;

import com.elim.learn.spring.cloud.zookeeper.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private HelloService helloService;

  @GetMapping
  public String hello() {
    return "response from HelloService is : " + helloService.hello();
  }

}
