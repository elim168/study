/**
 * 
 */
package com.elim.learn.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elim.learn.mybatis.model.User;

/**
 * @author Elim
 * 2016年12月20日
 */
public interface UserMapper {

	User findById(@Param("id") Long id);
	
	List<User> findByNameAndMobile(@Param("name") String name, String mobile);
	
	void insert(User user);
	void delete(Long id);
	void update(User user);
	
	List<User> findAll();
	
}
