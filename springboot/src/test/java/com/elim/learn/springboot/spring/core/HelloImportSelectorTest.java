package com.elim.learn.springboot.spring.core;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.elim.springboot.spring.core.importselector.HelloConfiguration;
import com.elim.springboot.spring.core.importselector.HelloService;

@ContextConfiguration(classes=HelloConfiguration.class)
@RunWith(SpringRunner.class)
public class HelloImportSelectorTest {

    @Autowired
    private List<HelloService> helloServices;
    
    @Test
    public void test() {
        this.helloServices.forEach(HelloService::doSomething);
    }
    
}
