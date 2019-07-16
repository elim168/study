package com.elim.study.dubbo.anno;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Elim
 * 19-7-16
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.elim.study.dubbo.anno.service.impl")
@PropertySource("classpath:/dubbo-provider.properties")
public class AnnotationConfiguration {
}
