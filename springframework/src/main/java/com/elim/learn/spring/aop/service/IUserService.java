/**
 * 
 */
package com.elim.learn.spring.aop.service;

import com.elim.learn.spring.support.ArgWithAnnotation;

/**
 * @author Elim
 * 2017年1月2日
 */
public interface IUserService {

	public void add();
	
	public void add(int orgId, String name);
	
	public void add(ArgWithAnnotation arg);
	
}
