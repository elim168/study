package com.elim.learn.springboot.spring.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.elim.springboot.Application;

@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class YamlProfileTest {
    
    @Autowired
    private Environment env;

    @Value("${app.ip}")
    private String appIp;
    
    @Test
    public void testDefault() {
        Assert.assertEquals("10.10.10.1", this.appIp);
    }
    
    @Test
    public void testDev() {
        Assert.assertEquals("10.10.10.2", this.appIp);
    }
    
    @Test
    public void testTest() {
        Assert.assertEquals("10.10.10.3", this.appIp);
    }
    
    @Test
    public void testProps() {
        System.out.println(this.env.getProperty("app.name"));
        System.out.println(this.env.getProperty("app.config.name"));
        System.out.println(this.env.getProperty("management.endpoints.jmx.exposure.exclude"));
    }
    
}
