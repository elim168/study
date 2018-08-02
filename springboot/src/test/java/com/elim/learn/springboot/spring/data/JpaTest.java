package com.elim.learn.springboot.spring.data;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elim.springboot.Application;
import com.elim.springboot.data.jpa.User;
import com.elim.springboot.data.jpa.UserService;

@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class JpaTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    public void test() {
        String[] names = {"张三", "李四", "王五"};
        for (int i=0; i<100; i++) {
            User user = new User();
            user.setName(names[i%names.length]);
            user.setUsername("user_" + i);
            this.userService.save(user);
        }
    }

    @Test
    public void testFindByName() {
        List<User> users = this.userService.findByName("李四");
        users.forEach(System.out::println);
    }
    
}
