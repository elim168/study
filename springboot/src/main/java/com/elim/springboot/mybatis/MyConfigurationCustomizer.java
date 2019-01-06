package com.elim.springboot.mybatis;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.stereotype.Component;

@Component
public class MyConfigurationCustomizer implements ConfigurationCustomizer {

    @Override
    public void customize(Configuration configuration) {
        configuration.setLazyLoadingEnabled(true);
    }

}
