/**
 * 
 */
package com.elim.learn.mybatis.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.mybatis.dao.SysWfNodeMapper;
import com.elim.learn.mybatis.dao.SysWfProcessMapper;
import com.elim.learn.mybatis.model.SysWfNode;
import com.elim.learn.mybatis.model.SysWfProcess;
import com.elim.learn.mybatis.util.SqlSessionFactoryUtil;

/**
 * @author Elim
 * 2016年10月13日
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
		for (int i=0; i<10; i++) {
			sysWfNode = new SysWfNode();
			sysWfNode.setProcessId(1);
			sysWfNode.setNodeCode("N" + (i+1));
			sysWfNode.setNodeName("节点" + (i+1));
			mapper.insertSelective(sysWfNode);
		}
	}
	
	@Test
	public void test3() {
		SysWfProcessMapper mapper = session.getMapper(SysWfProcessMapper.class);
		SysWfProcess process = mapper.singleSql1ToN(1);
		List<SysWfNode> nodes = process.getNodes();
		System.out.println(nodes);//这里可以输出获取到的SysWfNode信息
	}
	
	@Test
	public void test4() {
		SysWfNodeMapper mapper = session.getMapper(SysWfNodeMapper.class);
		SysWfNode node = mapper.singleSqlNTo1(2);
		SysWfProcess process = node.getProcess();
		System.out.println(process);//这里能拿到对应的SysWfProcess
	}
	
	@Test
	public void test5() {
		SysWfProcessMapper mapper = session.getMapper(SysWfProcessMapper.class);
		SysWfProcess process = mapper.singleSql1ToN(1);
		List<SysWfNode> nodes = process.getNodes();
		SysWfNode node = nodes.get(0);
		System.out.println(node.getProcess());//不为null
		System.out.println(process == node.getProcess());//false
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
	
}
