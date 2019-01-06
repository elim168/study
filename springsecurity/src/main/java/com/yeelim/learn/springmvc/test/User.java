/**
 * 
 */
package com.yeelim.learn.springmvc.test;

import java.util.ArrayList;

/**
 * @author elim
 * @date 2015-3-24
 * @time 下午7:53:58 
 *
 */
public class User {

	private int id;
	private ArrayList<Role> roles;

	/**
	 * 返回属性id
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 给属性id赋值
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 返回属性roles
	 * 
	 * @return the roles
	 */
	public ArrayList<Role> getRoles() {
		return roles;
	}

	/**
	 * 给属性roles赋值
	 * 
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", roles=").append(roles)
				.append("]");
		return builder.toString();
	}
	
}
