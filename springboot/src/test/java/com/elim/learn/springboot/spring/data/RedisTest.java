package com.elim.learn.springboot.spring.data;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.springboot.Application;

import lombok.Data;

@SpringBootTest(classes=Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Test
    public void basicOperation() {
        BoundValueOperations<String, String> boundValueOps = this.stringRedisTemplate.boundValueOps("key-string-value");
        boundValueOps.set("test string value", 60, TimeUnit.MINUTES);
        
        BoundListOperations<String, String> boundListOps = this.stringRedisTemplate.boundListOps("key-list-value");
        for (int i=0; i<10; i++) {
            boundListOps.leftPush("V_" + (i*10 + i+1));
        }
        
        List<String> list = boundListOps.range(0, boundListOps.size());
        list.forEach(System.out::println);
    }
    
    @Test
    public void test() {
        System.out.println("当前使用的RedisConnectionFactory是：" + this.redisTemplate.getConnectionFactory().getClass());
        ListOperations<Object, Object> opsForList = this.redisTemplate.opsForList();
        String key = "list1";
        opsForList.leftPush(key, "value1", "value2");
    }
    
    @Test
    public void operForUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("zhangsan");
        user.setName("张三");
        ValueOperations<Object, Object> opsForValue = this.redisTemplate.opsForValue();
        opsForValue.set(user, user);
        
        Object object = opsForValue.get(user);
        System.out.println(object);
        
        
        
        System.out.println("================================================");
        
        opsForValue.set("user::" + user.getId(), user);
        
        object = opsForValue.get("user::" + user.getId());
        System.out.println(object);
        
    }
    
    @Data
    public static class User implements Serializable {
        private static final long serialVersionUID = -1479529526911953462L;
        private Long id;
        private String username;
        private String name;
    }
    
}
