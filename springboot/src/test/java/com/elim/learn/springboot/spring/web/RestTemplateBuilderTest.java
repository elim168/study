package com.elim.learn.springboot.spring.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.springboot.Application;
import com.elim.springboot.spring.core.RestTemplateService;

@SpringBootTest(classes=Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RestTemplateBuilderTest {

    @Autowired
    private RestTemplateService restTemplateService;
    
    @Test
    public void test() {
        String something = this.restTemplateService.getSomething();
        System.out.println(something);
    }
    
    
}
