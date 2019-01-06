package com.elim.springboot.cache;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

@Component
public class EhcacheCacheManagerCustomizer implements CacheManagerCustomizer<EhCacheCacheManager> {

    @Override
    public void customize(EhCacheCacheManager cacheManager) {
        List<String> cacheNames = Arrays.asList("cacheName1", "cacheName2", "cacheName3", "cacheName4", "cacheName5", "cacheName6");
        cacheNames.forEach(cacheManager.getCacheManager()::addCacheIfAbsent);
    }

}

