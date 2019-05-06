package com.elim.learn.spring.cloud.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 2019/2/25
 */
@RestController
@RequestMapping("hello/gateway")
public class GatewayController {

  private int count = 0;

  @GetMapping("hystrix/{num}")
  public String hystrix(@PathVariable("num") int num) throws Exception {
    if(num > 10) {
      System.out.println("尝试第" + ++count + "次");
      throw new IllegalArgumentException("invalid arg " + num);
    }
    TimeUnit.SECONDS.sleep(num);
    return "request param is : " + num;
  }

  @GetMapping("hystrix/callback/{num}")
  public String hystrixCallback(@PathVariable("num") int num) {
    return "from hystrix callback, request param is " + num;
  }

}
