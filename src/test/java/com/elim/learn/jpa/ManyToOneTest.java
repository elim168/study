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
		User author = entityManager.getReference(User.class, 4);
		Article article = new Article();
		article.setAuthor(author);
		article.setTitle("标题-----");
		article.setContent("这是一篇什么什么样的文章。");
		entityManager.persist(article);
	}
	
	/**
	 * 对于单向多对一关联，如果在多的一方通过@ManyToOne的cascade属性指定了级联持久化，则基于Hibernate实现的JPA在持久化多的一方时不会把一的一方也一并持久化。
	 */
	@Test
	public void testCascadePersistence() {
		//直接new一个User对象
		User author = new User();
		author.setName("author abc");
		author.setAge(36);
		Article article = new Article();
		article.setTitle("title for test");
		article.setContent("content for test");
		//只持久化多的一方，article
		entityManager.persist(article);
	}
	
	/**
	 * 对于单向多对一关联，如果在多的一方通过@ManyToOne的cascade属性指定了级联merge，则基于Hibernate实现的JPA在merge新new出来的多的一方
	 * 时效果和进行persist操作的结果是类似的，其不会将一的一方也持久化
	 */
	@Test
	public void testCascadeMerge() {
		//直接new一个User对象
		User author = new User();
		author.setName("author abc");
		author.setAge(36);
		Article article = new Article();
		article.setTitle("title for test");
		article.setContent("content for test");
		//只持久化多的一方，article
		entityManager.merge(article);
		System.out.println("--------------------分界线--------------------");
	}
	
	/**
	 * 对于单向多对一关联，如果在多的一方通过@ManyToOne的cascade属性指定了级联merge，则基于Hibernate实现的JPA在merge从数据库查询
	 * 出来的实体对象时即使一的一方发生了改变也不会级联merge，只有在提交事务时才会将修改一并flush到数据库中。
	 */
	@Test
	public void testCascadeMerge2() {
		Article article = this.entityManager.find(Article.class, 6);
		article.setTitle("title for test");
		article.setContent("content for test");
		article.getAuthor().setName("name for test");
		//只持久化多的一方，article
		entityManager.merge(article);
		System.out.println("--------------------分界线--------------------");
	}
	
	/**
	 * 对于单向的多对一关联，如果在多的一方通过@ManyToOne的cascade属性指定了级联删除，则基于Hibernate实现的JPA会在删除多的一方时级联的把一的一方也一并删除了。
	 */
	@Test
	public void testCascadeRemove() {
		Article article = entityManager.find(Article.class, 2);
		//删除多的一方
		entityManager.remove(article);
	}
	
	/**
	 * 对于单向的多对一关联，如果在多的一方通过@ManyToOne的cascade属性指定了级联refresh，则基于Hibernate实现的JPA会在refresh多的一方时把一的一方也一并refresh了。
	 */
	@Test
	public void testCascadeRefresh() {
		Article article = entityManager.find(Article.class, 6);
		String prevTitle = article.getTitle();
		String prevAuthorName = article.getAuthor().getName();
		article.setTitle(prevTitle + "--reset");
		article.getAuthor().setName(prevAuthorName + "--reset");
		//对多的一方进行refresh操作
		entityManager.refresh(article);
		System.out.println(prevTitle.equals(article.getTitle()));//true
		System.out.println(prevAuthorName.equals(article.getAuthor().getName()));//true
	}
	
	/**
	 * 对于单向的多对一关联，如果在多的一方通过@ManyToOne的cascade属性指定了级联detach，则基于Hibernate实现的JPA会在detach多的一方时把一的一方也从持久化上下文中detach。
	 */
	@Test
	public void testCascadeDetach() {
		Article article = entityManager.find(Article.class, 6);
		article.setTitle("abc");
		article.getAuthor().setName("abc");
		//从持久化上下文中detach多的一方
		entityManager.detach(article);
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
