package com.elim.learn.jpa.spring.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.elim.learn.jpa.spring.dao.RoleDao;
import com.elim.learn.jpa.spring.entity.Role;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

	//使用@PersistenceContext标注在类型为EntityManager的成员变量上时，Spring
	//会在运行时自动的注入一个当前持久化上下文绑定的EntityManager
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void add(Role role) {
		System.out.println(entityManager);
		entityManager.persist(role);
	}

	@Override
	public Long getTotal() {
		System.out.println(entityManager);
		String jpql = "select count(1) from Role";
		Query query = entityManager.createQuery(jpql);
		return (Long) query.getSingleResult();
	}

}
