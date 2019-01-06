/**
 * 
 */
package com.elim.learn.spring.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * 该bean是多例类型
 * @author Elim
 * 2017年7月20日
 */
@Component
@Scope(value="prototype", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class BeanB {

	private Object a = new Integer (10000);
	
	public Object getA() {
		return a;
	}
	
}
