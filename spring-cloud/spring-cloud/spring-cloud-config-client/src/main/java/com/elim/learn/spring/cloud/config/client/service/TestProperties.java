package com.elim.learn.spring.cloud.config.client.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Elim
 * 2019/4/24
 */
@ConfigurationProperties("test")
@Component
@Data
public class TestProperties {

  private String info;

  private String foo;

}
