/**
 * 
 */
package com.elim.learn.spring.bean.registry;

import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

/**
 * @author Elim
 * 2017年9月20日
 */
public class CustomBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		boolean useDefaultFilters = false;//是否使用默认的filter，使用默认的filter意味着只扫描那些类上拥有Component、Service、Repository或Controller注解的类。
		String basePackage = "com.elim.learn.spring.bean";
		ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(useDefaultFilters);
		//指定considerMetaAnnotations="true"时则如果目标类上没有指定的注解，但是目标类上的某个注解上加上了指定的注解则该类也将匹配。比如：
		TypeFilter includeFilter = new AssignableTypeFilter(HelloInterface.class);
		beanScanner.addIncludeFilter(includeFilter);
		Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackage);
		for (BeanDefinition beanDefinition : beanDefinitions) {
			//beanName通常由对应的BeanNameGenerator来生成，比如Spring自带的AnnotationBeanNameGenerator、DefaultBeanNameGenerator等，也可以自己实现。
			String beanName = beanDefinition.getBeanClassName();
			registry.registerBeanDefinition(beanName, beanDefinition);
		}
	}

}
