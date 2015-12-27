package com.elim.learn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import com.elim.learn.jpa.entity.User;

public class UserTest {

	@Test
	public void testAdd() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		User user = new User();
		user.setName("张三");
		user.setAge(30);
		entityManager.persist(user);
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
}
