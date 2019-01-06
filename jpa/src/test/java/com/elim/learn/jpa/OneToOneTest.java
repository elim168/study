package com.elim.learn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.jpa.entity.MeetingRoom;
import com.elim.learn.jpa.entity.Projector;

/**
 * 
 *
 * @author elim
 *
 * @date 2016年1月10日 下午7:13:51
 *
 */
public class OneToOneTest {

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
		Projector projector = new Projector();
		projector.setNo("P001");
		MeetingRoom room = new MeetingRoom();
		room.setNo("MR001");
		room.setProjector(projector);
		entityManager.persist(projector);
		entityManager.persist(room);
	}
	
	/**
	 * 对于单向的一对一关联，在查询的时候默认会将关联的那一方也查出来。
	 */
	@Test
	public void testFind() {
		MeetingRoom room = entityManager.find(MeetingRoom.class, 1);
		System.out.println(room);
	}
	
	/**
	 * 对于单向的一对一关联，如果通过@OneToOne的cascade属性指定了级联持久化，则基于Hibernate实现的JPA在持久化时能够进行级联的持久化，如下
	 * 在持久化MeetingRoom的时候会级联持久化Projector,这点和单向的多对一不一样，倒是和单向的一对多一样可以级联的持久化。
	 */
	@Test
	public void testCascadePersistence() {
		Projector projector = new Projector();
		projector.setNo("P001");
		MeetingRoom room = new MeetingRoom();
		room.setNo("MR001");
		room.setProjector(projector);
		entityManager.persist(room);
	}
	
}
