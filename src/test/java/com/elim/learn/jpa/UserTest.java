package com.elim.learn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.jpa.entity.User;


/**
 * 
 * JPA基本用法的测试类
 *
 * @author elim
 *
 * @date 2016年1月1日 下午10:53:34
 *
 */
public class UserTest {

	/**
	 * 类似于Hibernate里面的SessionFactory，是专门创建EntityManager的
	 */
	private EntityManagerFactory entityManagerFactory;
	/**
	 * 类似于Hibernate里面的Session，用于实体对象的增删改查
	 */
	private EntityManager entityManager;
	/**
	 * 事务控制的接口，用于事务的启动、提交、回滚等
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
	 * JPA的persistence方法用于对象的持久化，只用于新增，如果在进行持久化时对应主键的对象已经存在于数据库中，则将抛出异常。
	 */
	@Test
	public void testPersist() {
		User user = new User();
		user.setName("张三");
		user.setAge(30);
		//将临时状态的实体对象进行持久化
		entityManager.persist(user);
	}
	
	/**
	 * <li>
	 * 	getReference方法通过主键查询数据库对应的记录，并获取该对象的一个引用，类似于Hibernate的load的方法，
	 * 其是懒加载形式的，只有在真正的访问对象时才会发出select语句从数据库查询对应的记录，比如调用对象的某一个方法。
	 * </li>
	 * <li>
	 * 	如果指定主键的实体对象在数据库中不存在，则在调用getReference()方法时不会抛出异常，而在第一次使用返回的对象时
	 * 将会抛出EntityNotFoundException。
	 * </li>
	 * <li>
	 * 	通过getReference方法获取的对象在第一次访问对象时如果对应的EntityManager已经关闭了，并且不在同一个事务范围内
	 * 则将抛出懒加载异常（基于Hibernate实现是这样的，JPA没有强制要求这种情况一定要抛出异常，但它建议这种情况是不被允许的）。
	 * 也就是说在下面的代码中如果在getReference后立即提交当前事务并且关闭当前的EntityManager，则在随后访问该实体对象时
	 * 将抛出懒加载异常。对于没有开启事务的，在关闭EntityManager后才第一次访问实体对象也会抛出懒加载异常。
	 * </li>
	 */
	@Test
	public void testGetReference() {
		//获取主键为1的User实体对象。
		User user = entityManager.getReference(User.class, 1);
		//如果在getReference后提交了当前事务，并且关闭了EntityManager，则在后续访问该实体对象时将抛出懒加载异常（基于Hibernate的实现）。
//		transaction.commit();
//		entityManager.close();
		System.out.println(user);
	}
	
	/**
	 * find方法是通过主键查询实体对象，类似于Hibernate中的get方法。<br/>
	 * 如果对应主键的实体对象在数据库中不存在，则将返回null。
	 */
	@Test
	public void testFind() {
		User user = entityManager.find(User.class, 1);
		System.out.println(user);
	}

	/**
	 * JPA的remove方法用于将持久化的对象删除。对应的参数必须是一个持久化对象，如果给定的参数不是一个持久化对象则将抛出IllegalArgumentException。
	 */
	@Test
	public void testRemove() {
		User user = entityManager.getReference(User.class, 3);
		entityManager.remove(user);
	}
	
	/**
	 * JPA的merge方法用于将实体对象的状态与持久化环境（数据库）中的进行合并，返回持久化后的对象。<br/>
	 * 如果需要进行merge的实体对象没有指定主键，则将直接进行新增操作。
	 */
	@Test
	public void testMerge1() {
		User user = new User();
		user.setName("张三");
		user.setAge(30);
		//返回持久化后的对象。merge方法的参数可以是一个临时状态的对象，也可以是刚从数据库查询出来的持久化对象
		User persistenceUser = entityManager.merge(user);
		System.out.println(user == persistenceUser);//false
		System.out.println(user.getId());//null
		System.out.println(persistenceUser.getId());//持久化后的主键
	}
	
	/**
	 * JPA的merge方法用于将实体对象的状态与持久化环境（数据库）中的进行合并，返回持久化后的对象。<br/>
	 * 如果需要进行merge的实体对象指定了主键，则分如下几种情况：
	 * <li>如果数据库中存在指定主键的实体对象，则将进行更新操作。</li>
	 * <li>如果数据库中不存在指定主键的实体对象，则将进行新增操作，新持久化的实体的主键将根据对应实体指定的主键策略产生。</li>
	 */
	@Test
	public void testMerge2() {
		User user = entityManager.find(User.class, 8);
		user.setName("李四");
		user.setAge(40);
		//返回持久化后的对象
		User persistenceUser = entityManager.merge(user);
		System.out.println(user == persistenceUser);//true，因为进行merge的对象本来就是一个持久化对象
	}
	
}
