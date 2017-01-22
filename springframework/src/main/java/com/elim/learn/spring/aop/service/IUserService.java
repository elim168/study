/**
 * 
 */
package com.elim.learn.spring.aop.service;

import com.elim.learn.spring.common.model.User;
import com.elim.learn.spring.support.ArgWithAnnotation;

/**
 * @author Elim
 * 2017年1月2日
 */
public interface IUserService {

	public void add() throws Exception;
	
	public void add(int orgId, String name);
	
	public void add(ArgWithAnnotation arg);
	
	public User findById(Integer id);
	
	public void delete(String name, int sex);
	
	public <T> void testParam(T param);
	
}
