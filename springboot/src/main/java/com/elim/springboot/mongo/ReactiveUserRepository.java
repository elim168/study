package com.elim.springboot.mongo;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import reactor.core.publisher.Flux;

public interface ReactiveUserRepository extends ReactiveSortingRepository<User, Long> {

    Flux<User> findByUsername(String username);
    
}
