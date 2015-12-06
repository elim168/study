/**
 * 
 */
package com.yeelim.learn.spring.security.service;

import java.io.Serializable;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Yeelim
 * @date 2014-8-6
 * @time 下午9:04:04 
 *
 */
public class AclUtil {

	private MutableAclService aclService;
	
	public void grantAdminToOwner(Class<?> clazz, Serializable id) {
		ObjectIdentity oi = new ObjectIdentityImpl(clazz, id);
		Sid sid = new PrincipalSid(SecurityContextHolder.getContext()
				.getAuthentication());
		Permission p = BasePermission.ADMINISTRATION;
		//这个过程不仅会创建Acl_object_identity，如果对应的Acl_sid和Acl_class不存在的话也会一并创建
		MutableAcl acl = aclService.createAcl(oi);
		acl.insertAce(acl.getEntries().size(), p, sid, true);
		//这个过程会真正的保存对应的权限信息，即上句新增的AccessControlEntry。
		aclService.updateAcl(acl);
	}
	
}
