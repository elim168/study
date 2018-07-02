package com.elim.learn.spring.task;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component("scheduledTasks")
public class ScheduledTasks {

    public void printTime() {
        System.out.println(LocalDateTime.now());
    }
    
}
