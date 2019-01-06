package com.elim.learn.springboot.mongo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.springboot.Application;
import com.elim.springboot.mongo.ReactiveUserRepository;
import com.elim.springboot.mongo.RxJava2UserRepository;
import com.elim.springboot.mongo.User;
import com.elim.springboot.mongo.UserRepository;

import io.reactivex.Flowable;
import io.reactivex.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest(classes=Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
    
    @Autowired
    private ReactiveUserRepository reactiveUserRepository;
    
    @Autowired
    private RxJava2UserRepository rxJava2UserRepository;
    
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
    
    @Test
    public void testReactive() {
        User user = new User();
        user.setUserId(4L);
        user.setName("李四");
        user.setUsername("lisi");
        Mono<User> mono = this.reactiveMongoTemplate.save(user);
        mono.block();
    }
    
    @Test
    public void testReactiveUserRepository() throws Exception {
        User user = new User();
        user.setUserId(System.currentTimeMillis());
        user.setName("李四");
        user.setUsername("lisi");
        Mono<User> mono = this.reactiveUserRepository.save(user);
        mono.subscribe(u -> System.out.println("saved success : " + u));
        
        //get users by username
        Flux<User> flux = this.reactiveUserRepository.findByUsername("lisi");
        flux.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }
    
    @Test
    public void testRxJava2UserRepository() throws Exception {
        User user = new User();
        user.setUserId(System.currentTimeMillis());
        user.setName("李四");
        user.setUsername("lisi");
        Single<User> single = this.rxJava2UserRepository.save(user);
        System.out.println("saved success : " + single.blockingGet());
        
        //get users by username
        Flowable<User> flowable = this.rxJava2UserRepository.findByUsername("lisi");
        flowable.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }
    
}
