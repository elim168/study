package com.elim.learn.spring.cloud.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * @author Elim
 * 2019/2/22
 */
@RestController
public class HelloController {

  @Value("${remote.home}")
  private URI home;

/*  @GetMapping("a/b/**")
  public ResponseEntity<byte[]> hello(ProxyExchange<byte[]> proxyExchange) {
    String path = proxyExchange.path("/a/b");//当前请求URL是/a/b/hello时，返回的就是/hello
    return proxyExchange.uri(this.home + path).get();
  }*/

}
