package com.elim.springboot.data.jpa;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserJpaRepository userRepository;
    
    public void save(User user) {
        this.userRepository.save(user);
    }
    
    public void save(List<User> users) {
        this.userRepository.saveAll(users);
    }
    
    public List<User> findByName(String name) {
        return this.userRepository.findByName(name);
    }
    
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }
    
    @PostConstruct
    public void init() {
        /*Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            this.findById(10L);
        }, 10, 20, TimeUnit.SECONDS);*/
    }
    
}
