package com.elim.learn.spring.cloud.zookeeper.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Elim
 * 2019/5/9
 */
@FeignClient("${feign.service1.name:service1}")
public interface HelloService {

  @GetMapping("hello")
  String hello();

}
