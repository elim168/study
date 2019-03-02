package com.elim.learn.spring.cloud.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Elim
 * 2019/2/25
 */
@RestController
@RequestMapping("hystrix/fallback")
public class HystrixFallbackController {

  @GetMapping
  public String fallback() {
    return "gateway fallback result";
  }

}
