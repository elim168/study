package com.elim.learn.spring.task;

import java.time.LocalDateTime;

public class QuartzTasks {

    public void print() {
        System.out.println(LocalDateTime.now() + "---Integrate Quartz..." + Thread.currentThread());
    }
    
}
