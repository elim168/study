package com.elim.learn.springboot.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.springboot.Application;
import com.elim.springboot.cache.SimpleService;


@SpringBootTest(classes=Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CacheTest {

    @Autowired
    private SimpleService simpleService;
    
    @Autowired
    private CacheManager springCacheManager;
    
    @Test
    public void cacheManager() {
        System.out.println("使用的CacheManager是：" + this.springCacheManager.getClass());
    }
    
    @Test
    public void testGetTime() throws Exception {
        long time1 = this.simpleService.getTime();
        TimeUnit.MILLISECONDS.sleep(100);
        long time2 = this.simpleService.getTime();
        Assert.assertEquals(time1, time2);
    }
    
    
}
