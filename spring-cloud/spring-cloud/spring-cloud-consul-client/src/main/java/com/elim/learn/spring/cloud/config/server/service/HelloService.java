package com.elim.learn.spring.cloud.config.server.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Elim
 * 2019/2/13
 */
@FeignClient("${feign.client.hello}")
public interface HelloService {

  @GetMapping("/hello")
  String hello();

  @GetMapping("/hello2")
  String hello2();

}
