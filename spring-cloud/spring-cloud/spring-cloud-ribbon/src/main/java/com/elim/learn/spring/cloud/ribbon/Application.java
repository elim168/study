package com.elim.learn.spring.cloud.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

import com.elim.learn.spring.cloud.ribbon.config.RibbonConfiguration;

@SpringBootApplication
@RibbonClient(value="hello")
@RibbonClients(defaultConfiguration=RibbonConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
