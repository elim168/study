package com.elim.learn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.jpa.entity.User;
import com.elim.learn.jpa.spring.entity.Role;
import com.elim.learn.jpa.spring.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class IntegrateSpringTest {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private RoleService roleService;
	
	@Test
	public void test1() {
		System.out.println(context);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println(entityManager);
		User user = entityManager.find(User.class, 1);
		System.out.println(user);
	}
	
	@Test
	public void test2() {
		System.out.println(roleService.getClass().getName());
		Role role = new Role();
		roleService.add(role);
		Role role2 = new Role();
		roleService.add(role2);
		System.out.println(roleService.getTotal());
	}
	
}
