package com.elim.springboot.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
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
        log.info(this.props.toString());
        /*while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("=--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-================--------------------==-=-=-=--==-=-=-=-=-=-==-===============");
        }*/
    }
    
}