/**
 * 
 */
package com.elim.learn.mybatis.model;

import com.elim.learn.mybatis.constants.OrgType;

/**
 * 员工
 * @author Elim
 * 2016年12月17日
 */
public class Person extends OrganizationBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1258186455048072583L;
	/**
	 * 员工的手机号码
	 */
	private String mobile;
	/**
	 * 员工的邮箱地址
	 */
	private String email;
	
	public Person() {
		super(OrgType.PERSON);
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
