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
 * 
 * 由于继承自ClassPathScanningCandidateComponentProvider，所以使用ClassPathScanningCandidateComponentProvider时
 * 指定的那些TypeFilter的操作在ClassPathBeanDefinitionScanner上也可以使用。
 * 
 * 
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

/**









ClassPathBeanDefinitionScanner继承自ClassPathScanningCandidateComponentProvider，构造时要求指定一个BeanDefinitionRegistry对象，
其扩展了一个scan方法，可以同时指定多个要扫描的包。底层在扫描bean定义时还是使用的findCandidateComponents方法，但是扫描后会自动利用持有的BeanDefinitionRegistry
自动对bean定义进行注册。注册bean定义的bean名称会使用持有的BeanNameGenerator生成，默认是AnnotationBeanNameGenerator；如果对应的bean定义是
AnnotatedBeanDefinition类型的，还会处理对应的一些注解定义。而使用ClassPathScanningCandidateComponentProvider时我们只能获取到对应的bean定义，另外的bean注册等还需要我们来做。
ClassPathBeanDefinitionScanner的核心代码如下：
```java
public int scan(String... basePackages) {
	int beanCountAtScanStart = this.registry.getBeanDefinitionCount();

	doScan(basePackages);

	// Register annotation config processors, if necessary.
	if (this.includeAnnotationConfig) {
		AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
	}

	return (this.registry.getBeanDefinitionCount() - beanCountAtScanStart);
}

protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
	Assert.notEmpty(basePackages, "At least one base package must be specified");
	Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();
	for (String basePackage : basePackages) {
		Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
		for (BeanDefinition candidate : candidates) {
			ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
			candidate.setScope(scopeMetadata.getScopeName());
			String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
			if (candidate instanceof AbstractBeanDefinition) {
				postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
			}
			if (candidate instanceof AnnotatedBeanDefinition) {
				AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
			}
			if (checkCandidate(beanName, candidate)) {
				BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
				definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
				beanDefinitions.add(definitionHolder);
				registerBeanDefinition(definitionHolder, this.registry);
			}
		}
	}
	return beanDefinitions;
}
```

假设现有HelloOne和HelloTwo两个类，分别位于包com.elim.learn.spring.bean.registry.one和com.elim.learn.spring.bean.registry.two下，它们类上都标注了
HelloAnnotation注解，如果现在需要只扫描包com.elim.learn.spring.bean.registry.one和com.elim.learn.spring.bean.registry.two下类上拥有HelloAnnotation
注解的类作为bean，则可以定义如下BeanDefinitionRegistryPostProcessor进行bean扫描。
```java
public class CustomBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

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
```

测试代码如下：
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={CustomBeanDefinitionRegistry.class})
public class CustomBeanDefinitionRegistryTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void assertBean() {
		Assert.assertNotNull(this.applicationContext.getBean(HelloOne.class));
		Assert.assertNotNull(this.applicationContext.getBean(HelloTwo.class));
	}
	
}
```

关于ClassPathBeanDefinitionScanner的更多信息请参考相应的API文档或源码。
（本文由Elim写于2017年9月29日）











*/