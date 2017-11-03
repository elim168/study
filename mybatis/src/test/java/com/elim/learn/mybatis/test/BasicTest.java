/**
 * 
 */
package com.elim.learn.mybatis.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.mybatis.dao.PersonMapper;
import com.elim.learn.mybatis.dao.SysWfNodeMapper;
import com.elim.learn.mybatis.dao.SysWfProcessMapper;
import com.elim.learn.mybatis.dao.UserMapper;
import com.elim.learn.mybatis.model.Person;
import com.elim.learn.mybatis.model.SysWfNode;
import com.elim.learn.mybatis.model.SysWfProcess;
import com.elim.learn.mybatis.model.User;
import com.elim.learn.mybatis.util.SqlSessionFactoryUtil;

/**
 * @author Elim 2016年10月13日
 *
 */
public class BasicTest {

	private SqlSessionFactory sessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
	private SqlSession session = null;

	@Before
	public void before() {
		session = sessionFactory.openSession();
	}

	@After
	public void after() {
		session.commit();
		session.close();
	}

	@Test
	public void test() {
		SysWfProcessMapper mapper = session.getMapper(SysWfProcessMapper.class);
		SysWfProcess process = new SysWfProcess();
		process.setCreateTime(new Date());
		process.setTemplateId(10);
		process.setCreator(1);
		mapper.insert(process);
	}

	@Test
	public void test2() {
		SysWfNodeMapper mapper = session.getMapper(SysWfNodeMapper.class);
		SysWfNode sysWfNode = null;
		for (int i = 0; i < 10; i++) {
			sysWfNode = new SysWfNode();
			sysWfNode.setProcessId(1);
			sysWfNode.setNodeCode("N" + (i + 1));
			sysWfNode.setNodeName("节点" + (i + 1));
			mapper.insertSelective(sysWfNode);
		}
	}

	@Test
	public void test3() {
		SysWfProcessMapper mapper = session.getMapper(SysWfProcessMapper.class);
		SysWfProcess process = mapper.singleSql1ToN(1);
		List<SysWfNode> nodes = process.getNodes();
		System.out.println(nodes);// 这里可以输出获取到的SysWfNode信息
	}

	@Test
	public void test4() {
		SysWfNodeMapper mapper = session.getMapper(SysWfNodeMapper.class);
		SysWfNode node = mapper.singleSqlNTo1(2);
		SysWfProcess process = node.getProcess();
		System.out.println(process);// 这里能拿到对应的SysWfProcess
	}

	@Test
	public void test5() {
		SysWfProcessMapper mapper = session.getMapper(SysWfProcessMapper.class);
		SysWfProcess process = mapper.singleSql1ToN(1);
		List<SysWfNode> nodes = process.getNodes();
		SysWfNode node = nodes.get(0);
		System.out.println(node.getProcess());// 不为null
		System.out.println(process == node.getProcess());// false
	}

	@Test
	public void test6() {
		SysWfProcessMapper mapper = session.getMapper(SysWfProcessMapper.class);
		Map<Integer, Integer> ids = new HashMap<>();
		ids.put(1, 2);
		ids.put(3, 4);
		ids.put(5, 6);
		List<SysWfProcess> list = mapper.findByForEach(ids.entrySet());
		System.out.println(list.size());
	}

	@Test
	public void test7() {
		SysWfProcessMapper mapper = session.getMapper(SysWfProcessMapper.class);
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		List<SysWfProcess> list = mapper.findByForEach2(ids);
		System.out.println(list.size());
	}

	@Test
	public void test8() {
		SysWfNodeMapper mapper = session.getMapper(SysWfNodeMapper.class);
		List<SysWfNode> list = mapper.fuzzyQuery("N1");
		System.out.println(list.size());
	}

	/**
	 * 默认是有一级缓存的，一级缓存只针对于使用同一个SqlSession的情况。<br/>
	 * 注意：当使用Spring整合后的Mybatis，即使是同一个Mapper接口对应的操作也是没有一级缓存的，因为它们是对应不同的SqlSession
	 */
	@Test
	public void testCache() {
		PersonMapper mapper = session.getMapper(PersonMapper.class);
		mapper.findById(5L);
		mapper.findById(5L);
	}

	@Test
	public void testCache2() {
		SqlSession session1 = this.sessionFactory.openSession();
		SqlSession session2 = this.sessionFactory.openSession();
		SqlSession session3 = this.sessionFactory.openSession();
		Person person1 = session1.getMapper(PersonMapper.class).findById(5L);
		person1.setEmail("Email1123");
		session1.commit();
		Person person2 = session2.getMapper(PersonMapper.class).findById(5L);
		person2.setMobile("18188888888");
		System.out.println(person2.getEmail());
		System.out.println(person1 == person2);
		Person person3 = session3.getMapper(PersonMapper.class).findById(5L);
		System.out.println(person3.getEmail());
		System.out.println(person3.getMobile());
	}

	@Test
	public void testGetCache() {
		Configuration configuration = this.session.getConfiguration();
		// this.sessionFactory.getConfiguration();
		Collection<Cache> caches = configuration.getCaches();
		System.out.println(caches);
	}

	@Test
	public void testLazyLoad1() {
		SysWfProcessMapper mapper = this.session.getMapper(SysWfProcessMapper.class);
		SysWfProcess process = mapper.selectByPrimaryKey(1);
		System.out.println(process.getClass());
		System.out.println(process);
		// System.out.println(Thread.currentThread().getName() +
		// "=========================" + new Date());
		// System.out.println(process.getNodes().iterator().next().getClass());
		// System.out.println(process.getTemplateId());
		// System.out.println(process.getId());
		// System.out.println(process.getNodes().size());
		// System.out.println(process);
	}

	@Test
	public void testLazyLoad2() {
		SysWfNodeMapper mapper = this.session.getMapper(SysWfNodeMapper.class);
		SysWfNode node = mapper.selectByPrimaryKey(1);
		System.out.println(node.getClass());
		System.out.println(node.getProcess().getClass());
	}

	@Test
	public void testExecutor() {
		SqlSession session = this.sessionFactory.openSession(ExecutorType.BATCH);
		UserMapper mapper = session.getMapper(UserMapper.class);
		List<User> users = new ArrayList<>();
		User user = null;
		for (int i = 0; i < 10; i++) {
			user = new User();
			user.setName("Name_" + i);
			user.setEmail("email");
			mapper.insert(user);
			users.add(user);
		}
		// 批量插入User
		session.commit();// 提交事务时批量操作才会写入数据库
		// for (User user1 : users) {
		// user1.setMobile("mobile");
		// mapper.update(user1);
		// }
		// session.commit();//批量更新User
		session.close();
	}

	@Test
	public void testJDBCBatch() throws SQLException {
		String sql = "insert into t_user(name, mobile, email) values(?,?,?)";
		try (Connection conn = session.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			List<User> users = this.getUsers();
			for (User user : users) {
				pstmt.setString(1, user.getName());
				pstmt.setString(2, user.getMobile());
				pstmt.setString(3, user.getEmail());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		}
	}

	private List<User> getUsers() {
		List<User> users = new ArrayList<>();
		User user = null;
		for (int i = 0; i < 10; i++) {
			user = new User();
			user.setName("Name+" + i);
			user.setEmail("Email+" + i);
			user.setMobile("Mobile+" + i);
			users.add(user);
		}
		return users;
	}

}
