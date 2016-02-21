package com.elim.learn.jpa.spring.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elim.learn.jpa.spring.dao.RoleDao;
import com.elim.learn.jpa.spring.entity.Role;
import com.elim.learn.jpa.spring.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Transactional
	@Override
	public void add(Role role) {
		roleDao.add(role);
	}

	@Override
	public Long getTotal() {
		return roleDao.getTotal();
	}

}
