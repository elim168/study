/**
 * 
 */
package com.elim.learn.spring.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Elim
 * 2017年9月14日
 */
@Configuration
@EnableAspectJAutoProxy
public class WithinTestConfiguration {

	@Bean
	public BeanA beanA() {
		return new BeanA();
	}
	
	@Bean
	public BeanB beanB() {
		return new BeanB();
	}
	
	@Bean
	public BeanC beanC() {
		return new BeanC();
	}
	
}
