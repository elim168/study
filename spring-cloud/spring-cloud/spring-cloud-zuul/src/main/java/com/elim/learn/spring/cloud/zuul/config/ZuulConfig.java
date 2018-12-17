package com.elim.learn.spring.cloud.zuul.config;

import org.springframework.cloud.netflix.zuul.filters.post.LocationRewriteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfig {

    /**
     * 在当前的版本，后端服务重定向后，在Zuul会再发起请求到重定向的地址，而不是浏览器发起重定向请求。
     * @return
     */
    @Bean
    public LocationRewriteFilter localtionRewriteFilter() {
        return new LocationRewriteFilter();
    }
    
}
