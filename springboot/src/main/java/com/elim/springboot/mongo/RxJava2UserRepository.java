package com.elim.springboot.mongo;

import org.springframework.data.repository.reactive.RxJava2SortingRepository;

import io.reactivex.Flowable;

public interface RxJava2UserRepository extends RxJava2SortingRepository<User, Long> {

    Flowable<User> findByUsername(String username);
    
}
