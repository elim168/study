package com.elim.learn.spring.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
public class AsyncClass {

    public void print() {
        System.out.println(Thread.currentThread());
    }
    
}
