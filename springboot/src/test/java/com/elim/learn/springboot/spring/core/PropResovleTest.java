package com.elim.learn.springboot.spring.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.springboot.Application;
import com.elim.springboot.core.PropResolveSample;
import com.elim.springboot.core.RandomExample;


@SpringBootTest(classes=Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PropResovleTest {

    @Autowired
    private PropResolveSample propResolveSample;
    @Autowired
    private Environment enviroment;
    
    @Autowired
    private RandomExample randomExample;
    
    @Test
    public void test() {
        Assert.assertEquals("valueA", this.propResolveSample.getPropA());
        Assert.assertEquals("valueB", this.propResolveSample.getPropB());
        Assert.assertEquals("valueAC", this.propResolveSample.getPropC());
        
        Assert.assertEquals("valueA", this.enviroment.getProperty("test.prop.a"));
        Assert.assertEquals("valueB", this.enviroment.getProperty("test.prop.b"));
        Assert.assertEquals("valueAC", this.enviroment.getProperty("test.prop.c"));
        
        System.out.println(this.propResolveSample.getList());
        System.out.println(this.propResolveSample.getList().size());
    }
    
    @Test
    public void testRandom() {
        System.out.println(this.randomExample.toString());
    }
    
    @Test
    public void testCommandLine() {
        Assert.assertEquals("CommandLineValue", this.enviroment.getProperty("commandLineProp"));
    }
    
}
