package com.elim.learn.spring.cloud.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elim.learn.spring.cloud.client.service.HelloService;

@RestController
@RequestMapping("hello")
public class HelloController {

    @Autowired
    private HelloService helloService;
    
    @GetMapping
    public String helloWorld() {
        return this.helloService.helloWorld();
    }
    
}
