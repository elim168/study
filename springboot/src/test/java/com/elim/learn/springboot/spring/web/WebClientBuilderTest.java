package com.elim.learn.springboot.spring.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.springboot.Application;
import com.elim.springboot.spring.core.WebClientService;

@SpringBootTest(classes=Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class WebClientBuilderTest {

    @Autowired
    private WebClientService service;
    
    @Test
    public void test() {
        String result = this.service.getJson();
        System.out.println(result);
    }
    
    
}
