package com.elim.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Elim
 * 19-1-16
 */
@Controller
@RequestMapping("freemarker")
public class FreemarkerController {

  @GetMapping
  public String index() {
    return "index";
  }

  @GetMapping("hello")
  public String hello(Map<String, Object> model) {
    model.put("message", "helloWorld!");
    model.put("list", Arrays.asList(10, 20, 30, 40, 50));
    return "hello";
  }

}

