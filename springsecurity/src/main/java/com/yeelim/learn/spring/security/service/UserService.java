/**
 * 
 */
package com.yeelim.learn.spring.security.service;

import java.util.List;

import com.yeelim.learn.spring.security.entity.User;

/**
 * @author Yeelim
 * @date 2014-6-15
 * @time 下午4:42:22 
 *
 */
public interface UserService {

	public void addUser(User user);
	
	public void updateUser(User user);
	
	public User find(int id);
	
	public void delete(int id);
	
	public void delete(List<Integer> ids, List<String> usernames);
	
	public List<User> findAll();
	
	/**
	 * 给指定用户principal或权限grantedAuthority授予指定对象，指定的权限permission
	 * @param clazz 对象类型
	 * @param id 对象的主键
	 * @param principalOrAuthority 用户名或权限名
	 * @param sidType 区分是授予给指定的用户还是授予给指定的权限
	 * @param permission 需要授予的权限
	 */
	public void authorize(Class<?> clazz, Integer id, String principalOrAuthority, Integer sidType, Integer permission);
	
	
}
