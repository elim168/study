/**
 * 
 */
package com.elim.learn.spring.test.bean.registry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.bean.registry.CustomBeanDefinitionRegistry2;
import com.elim.learn.spring.bean.registry.one.HelloOne;
import com.elim.learn.spring.bean.registry.two.HelloTwo;

/**
 * @author Elim
 * 2017年9月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={CustomBeanDefinitionRegistry2.class})
public class CustomBeanDefinitionRegistryTest2 {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void assertBean() {
		Assert.assertNotNull(this.applicationContext.getBean(HelloOne.class));
		Assert.assertNotNull(this.applicationContext.getBean(HelloTwo.class));
	}
	
}






















/**
BeanDefinitionRegistryPostProcessor继承自BeanFactoryPostProcessor，是一种比较特殊的BeanFactoryPostProcessor。BeanDefinitionRegistryPostProcessor中定义的`postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)`方法
可以让我们实现自定义的注册bean定义的逻辑。下面的示例中就新定义了一个名为hello，类型为Hello的bean定义。
```java
public class CustomBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		RootBeanDefinition helloBean = new RootBeanDefinition(Hello.class);
		//新增Bean定义
		registry.registerBeanDefinition("hello", helloBean);
	}

}
```

测试时采用的配置是基于Java类的配置，对应的配置类如下：
```java
@Configuration
public class SpringConfiguration {

	@Bean
	public CustomBeanDefinitionRegistry customBeanDefinitionRegistry() {
		return new CustomBeanDefinitionRegistry();
	}
	
}
```

测试如下：
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringConfiguration.class})
public class CustomBeanDefinitionRegistryTest {

	@Autowired
	private Hello hello;
	
	@Test
	public void test() {
		//能运行就说明hello是不为空的
		Assert.assertNotNull(hello);
	}
	
}
```

## ClassPathScanningCandidateComponentProvider

在使用自定义的BeanDefinitionRegistryPostProcessor来添加自定义的bean定义时可以配合ClassPathScanningCandidateComponentProvider一起使用，ClassPathScanningCandidateComponentProvider可以根据一定的规则扫描类路径下满足特定条件的Class来作为候选的bean定义。
ClassPathScanningCandidateComponentProvider在扫描时可以通过TypeFilter来指定需要匹配的类和需要排除的类，使用ClassPathScanningCandidateComponentProvider时可以通过构造参数useDefaultFilter指定是否需要使用默认的TypeFilter，默认的TypeFilter将包含类上拥有
@Component、@Service、@Repository、@Controller、@javax.annotation.ManagedBean和@javax.inject.Named注解的类。在扫描时需要指定扫描的根包路径。以下是一些使用ClassPathScanningCandidateComponentProvider扫描并注册bean定义的示例。  

### 1、扫描指定包及其子包下面的所有非接口和非抽象类。
```java
@Override
public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
	boolean useDefaultFilters = false;//是否使用默认的filter，使用默认的filter意味着只扫描那些类上拥有Component、Service、Repository或Controller注解的类。
	String basePackage = "com.elim.learn.spring.bean";
	ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(useDefaultFilters);
	TypeFilter includeFilter = new TypeFilter() {

		@Override
		public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
				throws IOException {
			return metadataReader.getClassMetadata().isConcrete();
		}
		
	};
	beanScanner.addIncludeFilter(includeFilter);
	Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackage);
	for (BeanDefinition beanDefinition : beanDefinitions) {
		//beanName通常由对应的BeanNameGenerator来生成，比如Spring自带的AnnotationBeanNameGenerator、DefaultBeanNameGenerator等，也可以自己实现。
		String beanName = beanDefinition.getBeanClassName();
		registry.registerBeanDefinition(beanName, beanDefinition);
	}
}
```

### 2、扫描指定包及其子包下面拥有指定注解的类。
```java
@Override
public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
	boolean useDefaultFilters = false;//是否使用默认的filter，使用默认的filter意味着只扫描那些类上拥有Component、Service、Repository或Controller注解的类。
	String basePackage = "com.elim.learn.spring.bean";
	ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(useDefaultFilters);
	TypeFilter includeFilter = new AnnotationTypeFilter(HelloAnnotation.class);
	beanScanner.addIncludeFilter(includeFilter);
	Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackage);
	for (BeanDefinition beanDefinition : beanDefinitions) {
		//beanName通常由对应的BeanNameGenerator来生成，比如Spring自带的AnnotationBeanNameGenerator、DefaultBeanNameGenerator等，也可以自己实现。
		String beanName = beanDefinition.getBeanClassName();
		registry.registerBeanDefinition(beanName, beanDefinition);
	}
}
```

AnnotationTypeFilter是Spring自带的一个TypeFilter，可以扫描指定的注解。AnnotationTypeFilter一共有三个构造方法，分别如下：
```java
public AnnotationTypeFilter(Class<? extends Annotation> annotationType) {
	this(annotationType, true, false);
}

public AnnotationTypeFilter(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations) {
	this(annotationType, considerMetaAnnotations, false);
}

public AnnotationTypeFilter(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations, boolean considerInterfaces) {
	super(annotationType.isAnnotationPresent(Inherited.class), considerInterfaces);
	this.annotationType = annotationType;
	this.considerMetaAnnotations = considerMetaAnnotations;
}
```
主要差别在于considerMetaAnnotations和considerInterfaces。
#### considerMetaAnnotations
指定<font color="red">considerMetaAnnotations="true"</font>时则如果目标类上没有指定的注解，但是目标类上的某个注解上加上了指定的注解则该类也将匹配。比如：
```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HelloAnnotation {

}
@HelloAnnotation
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HasHelloAnnotation {

}
@HasHelloAnnotation
public class Hello {

}
```
#### considerInterfaces
在上面的代码中定义了两个注解HelloAnnotation和HasHelloAnnotation，其中HasHelloAnnotation上加上了@HelloAnnotation注解，类Hello上面加上了@HasHelloAnnotation注解，则在通过AnnotationTypeFilter扫描标注有HelloAnnotation注解的类时，如果指定了considerMetaAnnotations="true"则类Hello也会被扫描到。  
指定<font color="red">considerInterfaces="true"</font>时，则如果对应的类实现的接口上拥有指定的注解时也将匹配。比如下面这种情况扫描加了HelloAnnotation注解的类时就会扫描到Hello类。
```java
@HelloAnnotation
public interface HelloInterface {

}
public class Hello implements HelloInterface {

}
```

#### 父类上拥有指定的注解
如果我们需要扫描的目标注解上是加了@Inherited注解的，则如果一个类上没有指定的目标注解，但是其父类拥有对应的注解，则也会被扫描到。比如我们将HelloAnnotation加上@Inherited注解。
```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HelloAnnotation {

}
```
Hello类上加上@HelloAnnotation注解。
```java
@HelloAnnotation
public class Hello {

}
然后HelloChild类继承自Hello类。
```java
public class HelloChild extends Hello {

}
```
这时候进行扫描时HelloChild也会被扫描到，但如果拿掉HelloAnnotation上的@Inherited，则HelloChild扫描不到。

### 3.扫描指定包及其子包下面能赋值给指定Class的Class
```java
@Override
public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
	boolean useDefaultFilters = false;//是否使用默认的filter，使用默认的filter意味着只扫描那些类上拥有Component、Service、Repository或Controller注解的类。
	String basePackage = "com.elim.learn.spring.bean";
	ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(useDefaultFilters);
	//指定considerMetaAnnotations="true"时则如果目标类上没有指定的注解，但是目标类上的某个注解上加上了指定的注解则该类也将匹配。比如：
	TypeFilter includeFilter = new AssignableTypeFilter(Hello.class);
	beanScanner.addIncludeFilter(includeFilter);
	Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackage);
	for (BeanDefinition beanDefinition : beanDefinitions) {
		//beanName通常由对应的BeanNameGenerator来生成，比如Spring自带的AnnotationBeanNameGenerator、DefaultBeanNameGenerator等，也可以自己实现。
		String beanName = beanDefinition.getBeanClassName();
		registry.registerBeanDefinition(beanName, beanDefinition);
	}
}
```
AssignableTypeFilter也是Spring内置的一个TypeFilter，用于扫描指定类型的类。只要目标类型能够赋值给指定的类型，则表示匹配。即如果指定的是一个接口，则所有直接或间接实现该接口的类都将被扫描到。  

基于ClassPathScanningCandidateComponentProvider的特性，我们常常可以利用它构建一个工具类用以扫描指定包路径下指定类型的Class，获取满足条件的Class，然后加以利用，这常用于需要扫描的类不是Spring bean的场景。  

（本文由Elim写于2017年9月21日）

*/