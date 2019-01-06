package com.elim.springboot.spring.core.importselector;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(HelloImportBeanDefinitionRegistrar.class)
public @interface HelloScan {

    @AliasFor("value")
    String[] basePackages() default {};
    
    @AliasFor("basePackages")
    String[] value() default {};
    
}
