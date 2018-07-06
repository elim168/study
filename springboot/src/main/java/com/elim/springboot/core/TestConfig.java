package com.elim.springboot.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Autowired
    private TestConfigurationProperties props;
    
    @Bean
    @ConfigurationProperties("test.config")
    public TestConfigurationProperties initTestConfigurationProperties() {
        return new TestConfigurationProperties();
    }
    
    @PostConstruct
    public void init() {
        System.out.println(this.props);
    }
    
}
