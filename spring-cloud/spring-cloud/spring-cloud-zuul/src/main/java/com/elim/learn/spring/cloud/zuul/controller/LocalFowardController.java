package com.elim.learn.spring.cloud.zuul.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("local")
public class LocalFowardController {

    @GetMapping("abc")
    public String abc() {
        return "ABC" + LocalDateTime.now();
    }
    
}
