package com.elim.learn.springboot.spring.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elim.springboot.Application;
import com.elim.springboot.spring.core.ValidationTestService;

@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class ValidationServiceTest {

    @Autowired
    private ValidationTestService validationTestService;
    
    @Test
    public void testNotNull() {
        Integer num = null;
        this.validationTestService.notNull(num);
    }
    
    @Test
    public void testNotNull2() {
        Integer num = 1;
        this.validationTestService.notNull(num);
    }
    
    @Test
    public void testNotBlank() {
        String str = null;
        this.validationTestService.notBlank(str);
    }
    
    @Test
    public void testNotBlank2() {
        String str = "";
        this.validationTestService.notBlank(str);
    }
    
    @Test
    public void testNotBlank3() {
        String str = "A";
        this.validationTestService.notBlank(str);
    }
    
    @Test
    public void testReturnPositive() {
        int num = 1;
        this.validationTestService.returnPositive(num);
    }
    
    @Test
    public void testReturnPositive2() {
        int num = -1;
        this.validationTestService.returnPositive(num);
    }
    
}
