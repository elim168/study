package com.elim.springboot.data.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);
    
}
