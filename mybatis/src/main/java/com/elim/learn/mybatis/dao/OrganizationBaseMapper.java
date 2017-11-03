/**
 * 
 */
package com.elim.learn.mybatis.dao;

import com.elim.learn.mybatis.model.OrganizationBase;

/**
 * @author Elim
 * 2016年12月17日
 */
public interface OrganizationBaseMapper {

	OrganizationBase findById(Long id);
	
	void delete(Long id);
	
	void insert(OrganizationBase orgBase);
	
	void update(OrganizationBase orgBase);
	
}
