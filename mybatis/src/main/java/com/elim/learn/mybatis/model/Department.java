/**
 * 
 */
package com.elim.learn.mybatis.model;

import com.elim.learn.mybatis.constants.OrgType;

/**
 * 部门
 * @author Elim
 * 2016年12月17日
 */
public class Department extends OrganizationBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4484737278565043262L;

	public Department() {
		super(OrgType.DEPT);
	}
	
}
