package com.elim.learn.jpa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.jpa.entity.Lock;
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
	
	/**
	 * 当只查询对象的某一个属性时默认返回的是该属性类型对应的List。<br/>
	 * 当查询的是对象的多个属性时，默认返回的是多个属性组成的数组的List。<br/>
	 * 当查询的是对象的多个属性时，如果希望返回的是对象的List时，则可以利用对应的属性来构造该对象，前提是该对象有对应的构造方法。<br/>
	 */
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
		//当查询的是对象的多个属性时默认返回的则是多个属性组成的一个数组的List集合
		List<Object[]> users2 = query2.getResultList();
		System.out.println(users2);
		
		//当查询的是对象的多个属性时如果希望返回该对象List，则可以通过查询出来的多个属性构造对应的对象。
		String jpql3 = "select new User(user.name, user.age) from User user";
		Query query3 = entityManager.createQuery(jpql3);
		List<User> users3 = query3.getResultList();
		System.out.println(users3);
	}
	
	/**
	 * 在给javax.persistence.Query设定参数时还可以通过指定的参数名来设定参数值，如果是java.util.Date或Calendar类型还可以
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testQuery5() throws ParseException {
		String jpql = "from User user where user.createdDate <= ? and user.createdDate >= :startDate";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, new Date());
		//通过setParameter(String name, Date value, TemporalType temporalType)来设定参数;
		query.setParameter("startDate", new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01"), TemporalType.DATE);
		//Query的getResultList()方法用于将查询结果作为一个List进行返回。
		List<User> users = query.getResultList();
		System.out.println(users);
	}
	
	/**
	 * JPA支持对Query进行命名，然后在多个地方基于命名的Query进行查询。具体实现方式是在实体类上使用@NamedQuery来定义一个查询，
	 * 其主要包含两个参数name和query，分别用来指定查询的名字和查询语句。然后就可以通过EntityManager的createNamedQuery()方法
	 * 来基于指定名字的Query来创建一个Query对象了。<br/>
	 * 但是在一个实体类上只能使用一个@NamedQuery进行标注，如果需要在一个实体类上使用多个@NamedQuery则可以使用@NamedQueries来标注，
	 * 其value参数接收一个@NamedQuery的数组，用以定义多个命名的Query。
	 */
	@Test
	public void testNamedQuery() {
		Query query = entityManager.createNamedQuery("queryName");
		query.setParameter(1, 35);
		System.out.println(query.getResultList());;
		
		Query query1 = entityManager.createNamedQuery("query1");
		query1.setParameter(1, "%张%");
		System.out.println(query1.getResultList());;
	}
	
	/**
	 * 当我们希望直接基于数据库表进行操作时，我们可以创建基于数据库的SQL查询，即createNativeQuery
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testNativeQuery() {
		String sql = "select user_name from t_user";
		Query query = entityManager.createNativeQuery(sql);
		//当只查询某一个字段时返回的是所有该字段对应的一个List
		List<String> users = query.getResultList();
		System.out.println(users);
		
		String sql2 = "select user_name, age from t_user";
		Query query2 = entityManager.createNativeQuery(sql2);
		//当查询的是多个字段时默认返回的则是多个字段组成的一个数组的List集合
		List<Object[]> users2 = query2.getResultList();
		System.out.println(users2);
	}
	
	/**
	 * JPQL也可以进行DDL操作，当我们的JPQL语句是进行DDL操作时需要调用Query的executeUpdate()方法。<br/>
	 * 我们可以通过它进行update和delete操作，insert操作貌似是不支持的。
	 */
	@Test
	public void testUpdate() {
		String jpql = "update User u set u.name = ?,u.age = ?,u.birthday = ? where u.id = ?";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, "LiSi").setParameter(2, 32).setParameter(3, new Date()).setParameter(4, 10);
		//执行更新操作
		query.executeUpdate();
		
		String jpql2 = "delete User u where u.id = ?";
		Query query2 = entityManager.createQuery(jpql2);
		query2.setParameter(1, 11);
		//执行更新操作
		query2.executeUpdate();
	}
	
	/**
	 * JPQL也支持利用group by进行分组查询筛选出满足分组条件的内容，分组字段也可以直接是一个对象，筛选出来查询出来的也可以直接是一个对象，
	 * 这就为我们在需要分组查询出某一个对象时简单了很多。
	 */
	@SuppressWarnings({"unchecked" })
	@Test
	public void testGroupBy() {
		//根据作者进行分组，找出发表文章数量超过一篇的作者
		String jpql = "select a.author from Article a group by a.author having count(1) > 1";
		Query query = entityManager.createQuery(jpql);
		List<User> authors = query.getResultList();
		System.out.println(authors);
	}
	
	/**
	 * left join fetch和left outer join fetch的效果是一样的，它可以在查询出某一个对象时利用左外关联的方式把该对象关联的其它对象一并查出来，
	 * 类似于我们设置fetch=FetchType.EAGER，而查询出来的还是我们的那个对象。类似于该示例中，其查询出来的还是Lock对象，但是其查询的时候通过左外关联的
	 * 形式把其关联的keys一并查询出来了，这样在我们需要获取Lock对象的keys属性时就不再需要去数据库中进行查询了。这样的好处是我们在查询对象时，有的地方可以使用
	 * 懒加载，而有的地方又可以不用，非常的方便。
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testLeftJoinFetch() {
		String jpql = "from Lock a left join fetch a.keys ";
		Query query = entityManager.createQuery(jpql);
		List<Lock> locks = query.getResultList();
		System.out.println(locks);
	}
	
	/**
	 * 对于两个本身就有关联的对象，基于对象的左关联，我们只需要关联对象即可，不需要去用on关键字
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testLeftJoin() {
		String jpql = "from Lock a left join a.keys b ";
		Query query = entityManager.createQuery(jpql);
		//返回的是一个数组组成的列表，数组中包含的是Lock和Key对应的表中的所有字段
		List<Object[]> locks = query.getResultList();
		System.out.println(locks);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testInnerJoin() {
		String jpql = "from Lock a inner join a.keys ";
		Query query = entityManager.createQuery(jpql);
		//返回的是一个数组组成的列表，数组中包含的是Lock和Key对应的表中的所有字段
		List<Object[]> locks = query.getResultList();
		System.out.println(locks);
	}
	
}
