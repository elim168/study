package com.elim.learn.springboot.autoconfigure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elim.autoconfigure.HelloBean;
import com.elim.springboot.Application;

@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class AutoConfigurationTest {

    @Autowired
    private HelloBean helloBean;
    
    @Test
    public void test() {
        helloBean.sayHello();
    }
    
}
