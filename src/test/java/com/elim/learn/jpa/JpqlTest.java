package com.elim.learn.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.jpa.entity.User;

public class JpqlTest {

	/**
	 * 类似于Hibernate里面的SessionFactory，是专门创建EntityManager的
	 */
	private EntityManagerFactory entityManagerFactory;
	/**
	 * 类似于Hibernate里面的Session，用于实体对象的增删改查
	 */
	private EntityManager entityManager;
	/**
	 * 事务控制的接口，用于事务的启动、提交、回滚和设置只读等
	 */
	private EntityTransaction transaction;
	
	/**
	 * 每个单元测试方法执行前都会执行的操作。
	 * 利用该方法来初始化EntityManagerFactory、EntityManager和EntityTransaction
	 */
	@Before
	public void before() {
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
	}
	
	/**
	 * 每个单元测试方法执行完成后都会执行的操作。
	 * 利用该方法来进行事务的提交和对应的资源的释放。
	 */
	@After
	public void after() {
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	/**
	 * 简单的基于JPQL的查询
	 */
	@Test
	public void testQuery1() {
		String jpql = "from User user where user.age < ?";
		//创建一个javax.persistence.Query
		Query query = entityManager.createQuery(jpql);
		//设置参数，参数的索引值是从1开始的。
		query.setParameter(1, 35);
		@SuppressWarnings("unchecked")
		List<User> users = query.getResultList();
		System.out.println(users);
	}
	
	/**
	 * 分页查询<br/>
	 * 主要通过Query的setFirstResult来指定第一条记录的索引位置，通过setMaxResults来指定一次查询出来的记录最大数来实现分页的效果。<br/>
	 * 如每页显示10条记录，如需要查询第3页数据时，则当设置第一条记录的索引为20，最大的记录数位10。
	 */
	@Test
	public void testQuery2() {
		String jpql = "from User user where user.age < ?";
		//创建一个javax.persistence.Query
		Query query = entityManager.createQuery(jpql);
		//设置参数，参数的索引值是从1开始的。
		query.setParameter(1, 35);
		//设置第一条记录的位置，索引值是从0开始的。
		query.setFirstResult(0);
		//设置本次查询最多查询出来的记录数，相当于每页的记录数。
		query.setMaxResults(5);
		@SuppressWarnings("unchecked")
		List<User> users = query.getResultList();
		System.out.println(users);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testQuery3() {
		String jpql = "from User user where user.age = ?";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, 39);
		//Query的getResultList()方法用于将查询结果作为一个List进行返回。
		List<User> users = query.getResultList();
		//Query的getSingleResult()方法用于在查询结果只有一条记录时将其作为一个对象返回，如果没有记录或者记录数超过1条时都将抛出异常。
		User user = (User) query.getSingleResult();
		System.out.println(users);
		System.out.println(user);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testQuery4() {
		String jpql = "select user.name from User user";
		Query query = entityManager.createQuery(jpql);
		//当只查询对象的某一个属性时返回的是所有该属性对应的一个List
		List<String> users = query.getResultList();
		System.out.println(users);
		
		String jpql2 = "select user.name, user.age from User user";
		Query query2 = entityManager.createQuery(jpql2);
		//当查询的是对象的多个属性时返回的则是多个属性组成的一个数组的List集合
		List<Object[]> users2 = query2.getResultList();
		System.out.println(users2);
	}
	
}
