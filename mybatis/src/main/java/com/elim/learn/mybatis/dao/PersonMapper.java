/**
 * 
 */
package com.elim.learn.mybatis.dao;

import com.elim.learn.mybatis.model.Person;

/**
 * @author Elim
 * 2016年12月17日
 */
public interface PersonMapper {

	Person findById(Long id);
	
	void delete(Long id);
	
	void insert(Person orgBase);
	
	void update(Person orgBase);
	
}
