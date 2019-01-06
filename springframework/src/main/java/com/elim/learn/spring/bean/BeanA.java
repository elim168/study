/**
 * 
 */
package com.elim.learn.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 该bean是单例类型，其持有一个多例类型的bean
 * @author Elim
 * 2017年7月20日
 */
@Component
@WithinAnnotation
public class BeanA {

	@Autowired
	private BeanB beanB;
	
	public BeanB getBeanB() {
		return beanB;
	}
	
}
