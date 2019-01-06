/**
 * 
 */
package com.elim.learn.mybatis.model;

import com.elim.learn.mybatis.constants.OrgType;

/**
 * 机构
 * @author Elim
 * 2016年12月17日
 */
public class Organization extends OrganizationBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7365400612704649240L;

	public Organization() {
		super(OrgType.ORG);
	}

}
