/**
 * 
 */
package com.elim.learn.mybatis.test;

import java.text.DecimalFormat;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.mybatis.dao.UserMapper;
import com.elim.learn.mybatis.model.User;
import com.elim.learn.mybatis.service.UserServiceImpl;

/**
 * 
 * @author Elim
 * 2016年12月20日
 */
@ContextConfiguration("classpath:/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserServiceImpl userService;
	
	
	@Test
	public void testInsert() {
		User user = null;
		String[] names = new String[]{"zhuoyi", "maer", "zhangsan", "lisi", "wangwu", "zhaoliu", "tianqi", "wangba"};
		DecimalFormat mobile = new DecimalFormat("15888880000");
		for (int i=10000; i<10001; i++) {
			user = new User();
			user.setName("User_" + names[i%8] + i/8);
			user.setEmail(user.getName() + "@Email.com");
			user.setMobile(mobile.format(i));
			user.setUsername("username+" + (i+10000));
			this.userMapper.insert(user);
		}
	}
	
	@Test
	public void testFind() {
		User user = this.userMapper.findById(1L);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void testFindByNameAndMobile() {
		String name = "User_zhuoyi0";
		String mobile = "15888880000";
		this.userMapper.findByNameAndMobile(name, mobile);
	}
	
	/**
	 * 测试嵌套批处理时的事务情况
	 */
	@Test
	public void testTransaction() {
		this.userService.add();
	}
	
	@Test
	public void testAutoMapping() {
		User user = this.userMapper.findById(1L);
		System.out.println(user);
	}
	
}  
