/**
 * 
 */
package com.elim.learn.spring.bean.registry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Elim
 * 2017年9月20日
 */
@Configuration
public class SpringConfiguration {

	@Bean
	public CustomBeanDefinitionRegistry customBeanDefinitionRegistry() {
		return new CustomBeanDefinitionRegistry();
	}
	
}
