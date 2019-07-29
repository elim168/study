package com.elim.study.dubbo.springboot.server.service;

import com.elim.study.dubbo.springboot.HelloService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author Elim
 * 19-7-29
 */
@Service(timeout = 2500)
public class HelloServiceImpl implements HelloService {
  @Override
  public String sayHello(String name) {
    return "Spring boot. Hello " + name;
  }
}
