/**
 * 
 */
package com.elim.learn.mybatis.model;

import com.elim.learn.mybatis.constants.OrgType;

/**
 * 岗位
 * @author Elim
 * 2016年12月17日
 */
public class Post extends OrganizationBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9076217740054430073L;

	public Post() {
		super(OrgType.POST);
	}
	
}
