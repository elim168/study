package com.elim.study.dubbo.anno.client;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Elim
 * 19-7-16
 */
@Configuration
@ComponentScan("com.elim.study.dubbo.anno.client.service")
@EnableDubbo
@PropertySource("classpath:/dubbo-consumer.properties")
public class ClientAnnotationConfiguration {
}
