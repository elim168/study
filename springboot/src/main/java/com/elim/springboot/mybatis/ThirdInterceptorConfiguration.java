package com.elim.springboot.mybatis;

import java.util.Properties;

import com.github.pagehelper.PageInterceptor;

//@Configuration
public class ThirdInterceptorConfiguration {
    
//    @Bean
    public PageInterceptor newPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("dialet", "mysql");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

}
