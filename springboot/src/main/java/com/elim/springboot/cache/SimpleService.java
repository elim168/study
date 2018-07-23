package com.elim.springboot.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SimpleService {

    @Cacheable("cacheName1")
    public long getTime() {
        return System.currentTimeMillis();
    }
    
}
