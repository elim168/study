package com.elim.learn.jpa;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.jpa.entity.User;

public class SecondLevelCacheTest {

	private EntityManagerFactory entityManagerFactory;
	
	@Before
	public void before() {
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
	}
	
	@After
	public void after() {
		entityManagerFactory.close();
	}
	
	/**
	 * 对于使用同一个EntityManager对实体对象进行find和getReference时,如果在当前的持久化上下文中已经存在了对应的实体对象，则将直接返回对应的对象，而不会发再SQL
	 * 到数据库查询
	 */
	@Test
	public void testFirstCache() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		User user = entityManager.find(User.class, 1);
		User user1 = entityManager.find(User.class, 1);
		User user2 = entityManager.getReference(User.class, 1);
		User user3 = entityManager.getReference(User.class, 1);
		System.out.println(user == user1);//true
		System.out.println(user1 == user2);//true
		System.out.println(user2 == user3);//true
		entityManager.close();
	}
	
	/**
	 * 二级缓存是指可以跨EntityManager的缓存，默认情况下是没有启用的，此时不同的EntityManager查询出来的同一主键的实体对象是不同的
	 */
	@Test
	public void testSecondCache1() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.find(User.class, 1);
		//获取二级缓存
		Cache cache = entityManagerFactory.getCache();
		System.out.println(cache.contains(User.class, 1));//false
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();
		//没有启用二级缓存时会再从数据库进行查询
		entityManager2.find(User.class, 1);
		entityManager.close();
		entityManager2.close();
	}

	/**
	 * 二级缓存是指可以跨EntityManager的缓存，默认情况下是没有启用的。如果要启用需要在persistence.xml中的shared-cache-mode元素进行指定，
	 * shared-cache-mode元素指定值有如下几种：<br/>
	 * <ul>
	 * 		<li>ALL：所有的实体类都将启用二级缓存</li>
	 * 		<li>NONE：所有的实体类都不启用二级缓存</li>
	 * 		<li>ENABLE_SELECTIVE：有选择性的启用，即只有使用@Cacheable(true)进行标注的实体才启用二级缓存，与@Cacheable等效</li>
	 * 		<li>DISABLE_SELECTIVE：有选择性的不启用，即只有使用@Cacheable(false)进行标注的实体才不启用二级缓存</li>
	 * 		<li>UNSPECIFIED：未指定，默认值。此种情况将采用JPA实现者的默认策略</li>
	 * </ul>
	 * 这里我们采用ENABLE_SELECTIVE，并在User实体类中采用@Cacheable(true)进行标注，以启用其二级缓存。<br/>
	 * JPA启用了二级缓存后，还需要启用JPA实现者的二级缓存。
	 */
	@Test
	public void testSecondCache2() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.find(User.class, 1);
		Cache cache = entityManagerFactory.getCache();
		System.out.println(cache.contains(User.class, 1));//true
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();
		//不会再从数据库进行查询
		entityManager2.find(User.class, 1);
		entityManager.close();
		entityManager2.close();
	}
	
}
