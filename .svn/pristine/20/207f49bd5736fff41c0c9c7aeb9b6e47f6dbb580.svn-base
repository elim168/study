/**
 * 
 */
package com.yeelim.learn.spring.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yeelim.learn.spring.security.BasePermissionExt;
import com.yeelim.learn.spring.security.entity.User;
import com.yeelim.learn.spring.security.service.UserService;

/**
 * @author Yeelim
 * @date 2014-6-15
 * @time 下午4:45:48 
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MutableAclService aclService;
	
	public void addUser(User user) {
		System.out.println("addUser................" + user);
		
		//1、构建一个ObjectIdentity
		ObjectIdentity oi = new ObjectIdentityImpl(User.class, user.getId());

		//2、创建一个Acl、此时会如果对应的信息不存在会依次创建，如当前用户对应的Sid、ObjectIdentity对应于acl_class表中的类型
		//最后是往acl_object_identity中插入对应的数据
		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(oi);
		} catch (NotFoundException e) {
			acl = aclService.createAcl(oi);
		}

		//基于principal的Sid
		Sid sid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
		Permission p = BasePermission.ADMINISTRATION;//管理员权限
		acl.insertAce(acl.getEntries().size(), p, sid, true);
		acl = aclService.updateAcl(acl);
		System.out.println(acl);
		
	}
	
	public void authorize(Class<?> clazz, Integer id, String principalOrAuthority, Integer sidType, Integer permission) {
		ObjectIdentity oi = new ObjectIdentityImpl(clazz, id);
		Sid sid = null;
		if (sidType == 1) {	//用户
			sid = new PrincipalSid(principalOrAuthority);	//用户主体
		} else {
			sid = new GrantedAuthoritySid(principalOrAuthority);	//权限主体
		}
		Permission p = new BasePermissionExt(permission);

		MutableAcl acl = (MutableAcl) aclService.readAclById(oi);

		acl.insertAce(acl.getEntries().size(), p, sid, true);
		aclService.updateAcl(acl);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void updateUser(User user) {
		System.out.println("updateUser.............." + user);
		
	ObjectIdentity oi = new ObjectIdentityImpl(User.class, user.getId());
	//获取ObjectIdentity对应的Acl
	MutableAcl acl = (MutableAcl) aclService.readAclById(oi);
	acl.setEntriesInheriting(false);//内存中修改为不从父Acl继承AccessControlEntry
	aclService.updateAcl(acl);//同步到数据库
		
		
	}

	@PreAuthorize("hasPermission(#id, 'com.yeelim.learn.spring.security.entity.User', 1)")
	public User find(int id) {
		User user = new User();
		user.setId(id);
		return user;
	}

	public void delete(int id) {
		System.out.println("delete user by id................");
		ObjectIdentity objectIdentity = new ObjectIdentityImpl(User.class, id);
		aclService.deleteAcl(objectIdentity, true);
	}
	
	@PreFilter(filterTarget="ids", value="filterObject%2==0")
	public void delete(List<Integer> ids, List<String> usernames) {
		for (Integer i : ids) {
			System.out.println(i);
		}
	}

	@PostFilter("filterObject.id%2==0")
	public List<User> findAll() {
		List<User> userList = new ArrayList<User>();
		User user;
		for (int i=0; i<10; i++) {
			user = new User();
			user.setId(i);
			userList.add(user);
		}
		return userList;
	}

}
