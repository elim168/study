/**
 * 
 */
package com.elim.learn.spring.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @author Elim
 * 2017年8月9日
 */
public class Configuration {
	@Bean
	@Scope(value="prototype", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public Hello hello() {
		return new Hello();
	}
}

class Hello {
	
}