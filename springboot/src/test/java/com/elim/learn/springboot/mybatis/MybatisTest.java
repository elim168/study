package com.elim.learn.springboot.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elim.springboot.MybatisApplication;
import com.elim.springboot.mybatis.MybatisUserService;
import com.elim.springboot.mybatis.User;

@SpringBootTest(classes=MybatisApplication.class)
@RunWith(SpringRunner.class)
public class MybatisTest {

    @Autowired
    private MybatisUserService userService;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    @Test
    public void test() {
        User user = this.userService.findByUsername("user_1");
        System.out.println(user);
    }
    
    @Test
    public void testFindById() {
        User user = this.userService.findById(101L);
        System.out.println(user);
        System.out.println(this.sqlSessionTemplate.getExecutorType());
    }
    
    @Test
    public void testPageHelper() {
        System.out.println(this.userService.findAll(1, 5));
        System.out.println(this.userService.findAll(2, 5));
    }
    
}
