package com.elim.learn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.jpa.entity.Employee;
import com.elim.learn.jpa.entity.Post;

/**
 * 多对多关联关系测试类
 *
 * @author elim
 *
 * @date 2016年1月10日 下午8:57:14
 *
 */
public class ManyToManyTest {

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
	
	@Test
	public void testPersist() {
		Post post = new Post();
		post.setName("岗位1");
		Employee employee = new Employee();
		employee.setName("员工1");
		post.getEmployees().add(employee);
		employee.getPosts().add(post);
		entityManager.persist(post);
		entityManager.persist(employee);
	}

	/**
	 * 基于Hibernate实现的JPA在通过@ManyToMany的cascade属性指定了多对多级联持久化时可以进行级联持久化
	 */
	@Test
	public void testCascadePersist() {
		Post post = new Post();
		post.setName("岗位2");
		Employee employee = new Employee();
		employee.setName("员工2");
		post.getEmployees().add(employee);
		employee.getPosts().add(post);
		//只持久化employee
		entityManager.persist(employee);
	}

	/**
	 * 多对多关联，在进行查询时默认会对关联的对象进行懒加载
	 */
	@Test
	public void testFind() {
		Post post = entityManager.find(Post.class, 2);
		System.out.println(post.getName());
		System.out.println(post.getEmployees().size());
	}
	
}
