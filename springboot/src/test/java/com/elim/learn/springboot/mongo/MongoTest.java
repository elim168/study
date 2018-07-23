package com.elim.learn.springboot.mongo;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.springboot.Application;
import com.elim.springboot.mongo.User;
import com.elim.springboot.mongo.UserRepository;

@SpringBootTest(classes=Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testSave() {
        User user = new User();
        user.setUserId(2L);
        user.setName("张三");
        user.setUsername("zhangsan");
        this.mongoTemplate.save(user);
    }
    
    @Test
    public void testSave2() {
        User user = new User();
        user.setUserId(3L);
        user.setName("张三");
        user.setUsername("zhangsan");
        this.userRepository.save(user);
    }
    
    @Test
    public void testFindByName() {
        List<User> users = this.userRepository.findByName("张三");
        Assert.assertNotNull(users);
        Assert.assertEquals("张三", users.get(0).getName());
    }
    
}
