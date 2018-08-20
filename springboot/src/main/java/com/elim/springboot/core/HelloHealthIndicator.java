package com.elim.springboot.core;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HelloHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if (System.currentTimeMillis()%2==0) {
            return Health.down().withDetail("error", "详细信息，可以是一个对象").build();
        }
        return Health.up().build();
    }

}
