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
@RequestMapping("jsp")
public class JspController {

  @GetMapping
  public String index() {
    return "index";
  }

  @GetMapping("second")
  public String second(Map<String, Object> model) {
    model.put("message", "helloWorld!");
    model.put("list", Arrays.asList(1, 2, 3, 4, 5, 6));
    return "second";
  }

}

