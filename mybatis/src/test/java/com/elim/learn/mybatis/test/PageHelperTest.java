/**
 * 
 */
package com.elim.learn.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.elim.learn.mybatis.dao.UserMapper;
import com.elim.learn.mybatis.model.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author Elim
 * 2017年5月31日
 */
public class PageHelperTest {
	
	private static SqlSessionFactory sqlSessionFactory;
	private SqlSession session;
	
	@BeforeClass
	public static void beforeClass() throws IOException {
		InputStream is = Resources.getResourceAsStream("mybatis-config-single.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
	}
	
	@Before
	public void before() {
		this.session = sqlSessionFactory.openSession();
	}
	
	@After
	public void after() {
		this.session.close();
	}

	@Test
	public void test() {
		int pageNum = 2;//页码，从1开始
		int pageSize = 10;//每页记录数
		PageHelper.startPage(pageNum, pageSize);//指定开始分页
		UserMapper userMapper = this.session.getMapper(UserMapper.class);
		List<User> all = userMapper.findAll();
		Page<User> page = (Page<User>) all;
		System.out.println(page.getPages());
		System.out.println(page);
	}
	
}
