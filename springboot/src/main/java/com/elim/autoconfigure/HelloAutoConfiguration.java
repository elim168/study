package com.elim.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Hello.class)
@ConditionalOnMissingBean(HelloBean.class)
public class HelloAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "autoconfigure.hello", name = "enabled", matchIfMissing = true)
    @ConfigurationProperties("autoconfigure.hello")
    public HelloBean helloBean() {
        return new HelloBean();
    }

}
