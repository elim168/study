package com.elim.springboot.spring.core;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingTasks {

    
    @Scheduled(fixedRate=1000*60)
    public void fixedRate() {
        System.out.println(LocalDateTime.now() + "--------fixdRate");
    }
    
    @Scheduled(fixedDelay=1000*66)
    public void fixedDelay() {
        System.out.println(LocalDateTime.now() + "--------fixedDelay");
    }
    
    @Scheduled(cron="0 */3 * * * ?")
    public void cron() {
        System.out.println(LocalDateTime.now() + "--------cron");
    }
    
    
}
