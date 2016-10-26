package com.elim.learn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.jpa.entity.Key;
import com.elim.learn.jpa.entity.Lock;

/**
 * 单向的一对多测试用例
 *
 * @author elim
 *
 * @date 2016年1月9日 上午11:53:38
 *
 */
public class OneToManyTest {
	
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
	 * 对于单向的一对多关联，如果双方都是临时状态的，在持久化时无论是先持久化一的一方还是多的一方，都将在双方持久化完成后发出update语句更新多的一方关联的一的一方对应的
	 * 外键，因为在实体方面关系是由一的一方维护的，而在数据库层面是由多的一方对应的表维护的。
	 */
	@Test
	public void testPersist() {
		//一的一方，锁
		Lock lock = new Lock();
		lock.setNo("L002");
		//多的一方，钥匙
		Key key = new Key();
		key.setNo("K002");
		lock.getKeys().add(key);
		//先持久一的一方
		entityManager.persist(lock);
		//再持久化多的一方
		entityManager.persist(key);
		System.out.println("-------------------------------------------------");
	}
	
	/**
	 * 在基于Hibernate实现的JPA中，对于单向的一对多关联，在查询一的一方时默认多的一方采用懒加载的形式，可通过@OneToMany的fetch属性的FetchType.EAGER指定
	 * 为一起加载
	 */
	@Test
	public void testFind() {
		//打印出来的SQL语句中不会有Key相关的信息
		entityManager.find(Lock.class, 1);
		System.out.println("--------------------------------------------");
		
	}
	
	/**
	 * 对于单向一对多关联，如果在一的一方通过@OneToMany的cascade属性指定了级联持久化，则基于Hibernate实现的JPA在持久化一的一方时也会把多的一方也一并持久化。
	 */
	@Test
	public void testCascadePersistence() {
		//直接new一个Lock对象
		Lock lock = new Lock();
		lock.setNo("L004");
		Key key = new Key();
		key.setNo("K004");
		lock.getKeys().add(key);
		//只持久化一的一方，lock
		entityManager.persist(lock);
	}
	
	/**
	 * 对于单向一对多关联，如果在一的一方通过@OneToMany的cascade属性指定了级联merge，则基于Hibernate实现的JPA在merge新new出来的一的一方
	 * 时效果和进行persist操作的结果是类似的，其也会将多的一方也持久化
	 */
	@Test
	public void testCascadeMerge() {
		//直接new一个Lock对象
		Lock lock = new Lock();
		lock.setNo("L005");
		Key key = new Key();
		key.setNo("K005");
		lock.getKeys().add(key);
		//merge一的一方
		entityManager.merge(lock);
	}
	
	/**
	 * 对于单向一对多关联，如果在一的一方通过@OneToMany的cascade属性指定了级联merge，则基于Hibernate实现的JPA在merge从数据库查询
	 * 出来的实体对象时也会级联合并多的一方。
	 */
	@Test
	public void testCascadeMerge2() {
		Lock lock = entityManager.find(Lock.class, 1);
		Key key = new Key();
		key.setNo("K006");
		lock.getKeys().add(key);
		entityManager.merge(lock);
	}
	
	/**
	 * 对于单向的一对多关联，如果在一的一方通过@OneToMany的cascade属性指定了级联删除，则基于Hibernate实现的JPA会在删除一的一方时级联的把多的一方也一并删除了。
	 */
	@Test
	public void testCascadeRemove() {
		Lock lock = entityManager.find(Lock.class, 6);
		//删除一的一方
		entityManager.remove(lock);
	}
	
	/**
	 * 对于单向的一对多关联，如果在一的一方通过@OneToMany的cascade属性指定了级联refresh，则基于Hibernate实现的JPA会在refresh一的一方时把多的一方也一并refresh了。
	 */
	@Test
	public void testCascadeRefresh() {
		Lock lock = entityManager.find(Lock.class, 5);
		String prevNo = lock.getNo();
		String prevKeyNo = lock.getKeys().iterator().next().getNo();
		lock.setNo(prevNo + "1");
		lock.getKeys().iterator().next().setNo(prevKeyNo + "1");
		//对多的一方进行refresh操作
		entityManager.refresh(lock);
		System.out.println(prevNo.equals(lock.getNo()));//true
		System.out.println(prevKeyNo.equals(lock.getKeys().iterator().next().getNo()));//true
	}
	
	/**
	 * 对于单向的一对多关联，如果在一的一方通过@OneToMany的cascade属性指定了级联detach，则基于Hibernate实现的JPA会在detach一的一方时把多的一方也从持久化上下文中detach。<br/>
	 * 证明测试结果正确的是在提交事务时不会发出update语句。
	 */
	@Test
	public void testCascadeDetach() {
		Lock lock = entityManager.find(Lock.class, 5);
		lock.setNo("L006");
		lock.getKeys().iterator().next().setNo("K006");
		//从持久化上下文中detach一的一方
		entityManager.detach(lock);
	}

}
