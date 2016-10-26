package com.elim.learn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.jpa.entity.Class;
import com.elim.learn.jpa.entity.Student;

/**
 * 双向的多对一关联的简单测试用例
 *
 * @author elim
 *
 * @date 2016年1月10日 下午12:06:12
 *
 */
public class ManyToOneDblTest {

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
	 * 在保存时单纯的由多的一方来维持关联关系。
	 */
	@Test
	public void testPersist() {
		Class class1 = new Class();
		class1.setNo("001");
		class1.setName("一班");
		Student zhangsan = new Student();
		zhangsan.setNo("001");
		zhangsan.setName("张三");
		zhangsan.setOwnClass(class1);
		class1.getStudents().add(zhangsan);
		entityManager.persist(class1);
		entityManager.persist(zhangsan);
	}
	
	/**
	 * 对于双向的一对多关联，我们在查询获取到实体对象后，既能通过一的一方取到多的一方，也能通过多的一方取到一的一方。
	 */
	@Test
	public void testFind() {
		Class clazz = entityManager.find(Class.class, 1);
		System.out.println(clazz.getStudents().size());
		
		Student student = entityManager.find(Student.class, 2);
		System.out.println(student.getOwnClass().getName());
	}
	
}
