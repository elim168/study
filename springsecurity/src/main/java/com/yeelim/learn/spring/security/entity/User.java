/**
 * 
 */
package com.yeelim.learn.spring.security.entity;

import java.io.Serializable;

/**
 * @author Yeelim
 * @date 2014-6-15
 * @time 下午4:42:52 
 *
 */
public class User implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String email;
	private int sex;
	
	public User() {}
	
	/**
	 * @param id
	 */
	public User(Integer id) {
		this.id = id;
	}
	/**
	 * 返回属性id
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 给属性id赋值
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 返回属性username
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 给属性username赋值
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 返回属性password
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 给属性password赋值
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 返回属性name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 给属性name赋值
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 返回属性email
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 给属性email赋值
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 返回属性sex
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}
	/**
	 * 给属性sex赋值
	 * @param sex the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", username=")
				.append(username).append(", password=").append(password)
				.append(", name=").append(name).append(", email=")
				.append(email).append(", sex=").append(sex).append("]");
		return builder.toString();
	}
	
}
