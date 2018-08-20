package com.elim.springboot.spring.core.importselector;

import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

public class HelloImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(HelloScan.class.getName());
        String[] basePackages = (String[]) annotationAttributes.get("basePackages");
        if (basePackages == null || basePackages.length == 0) {//HelloScan的basePackages默认为空数组
            String basePackage = null;
            try {
                basePackage = Class.forName(importingClassMetadata.getClassName()).getPackage().getName();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            basePackages = new String[] {basePackage};
        }
        
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
        TypeFilter helloServiceFilter = new AssignableTypeFilter(HelloService.class);
        scanner.addIncludeFilter(helloServiceFilter);
        scanner.scan(basePackages);
    }

}
