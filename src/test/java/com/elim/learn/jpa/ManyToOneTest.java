package com.elim.learn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.jpa.entity.Article;
import com.elim.learn.jpa.entity.User;

/**
 * 单向的多对一的简单测试用例
 *
 * @author elim
 *
 * @date 2016年1月2日 下午10:03:44
 *
 */
public class ManyToOneTest {

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
	 * 保存多的一方时会自动把一的那一方的主键存到关联的字段中去。
	 */
	@Test
	public void testPersistence() {
		User author = entityManager.getReference(User.class, 2);
		Article article = new Article();
		article.setAuthor(author);
		article.setTitle("标题-----");
		article.setContent("这是一篇什么什么样的文章。");
		entityManager.persist(article);
	}
	
	/**
	 * 如果没有指定一的一方为懒加载，则在查询多的一方时就自动的通过左关联的方式把一的一方查询出来，而如果指定了一的一方是懒加载，
	 * 则只有在需要通过多的一方访问一的一方时才会对一的一方发起查询。
	 */
	@Test
	public void testFind() {
		Article article = entityManager.find(Article.class, 1);
		System.out.println(article.getTitle());
		System.out.println(article.getAuthor());
	}
	
}
