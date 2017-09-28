package com.elim.learn.spring.bean.registry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

/**
 * ClassPathBeanDefinitionScanner继承自ClassPathScanningCandidateComponentProvider，并定义了一个scan方法，
 * 该方法可以接收多个包路径，其会依次扫描在每个包中定义的bean，并使用BeanDefinitionRegistry进行bean注册，注册bean
 * 定义的bean名称会使用持有的BeanNameGenerator生成，默认是AnnotationBeanNameGenerator；如果对应的bean定义是
 * AnnotatedBeanDefinition类型的，还会处理对应的一些注解定义。而使用
 * ClassPathScanningCandidateComponentProvider时我们只能获取到对应的bean定义，另外的bean注册等还需要我们来做。
 * @author Elim
 * 2017年9月28日
 */
public class CustomBeanDefinitionRegistry2 implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
		TypeFilter includeFilter = new AnnotationTypeFilter(HelloAnnotation.class);
		scanner.addIncludeFilter(includeFilter);
		String[] basePackages = {"com.elim.learn.spring.bean.registry.one", "com.elim.learn.spring.bean.registry.two"};
		scanner.scan(basePackages);
	}

}
