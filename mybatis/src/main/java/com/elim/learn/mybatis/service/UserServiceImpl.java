/**
 * 
 */
package com.elim.learn.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elim.learn.mybatis.dao.UserMapper;
import com.elim.learn.mybatis.model.User;

/**
 * @author Elim
 * 2016年12月26日
 */
@Service
public class UserServiceImpl {

	@Autowired
	private UserMapper mapper;
//	@Autowired
//	private SqlSessionFactory sessionFactory;
	
	@Autowired
	private SqlSession session;
	
	@Transactional
	public void add() {
		User user = new User();
		user.setMobile("18888888888");
		mapper.insert(user);
//		SqlSession session = this.sessionFactory.openSession(ExecutorType.BATCH);
//		SqlSessionTemplate session = null;
//		session = new SqlSessionTemplate(this.sessionFactory, ExecutorType.BATCH);
		UserMapper mapper2 = session.getMapper(UserMapper.class);
		for (int i=0; i<10; i++) {
			user = new User();
			user.setMobile("2888888888" + i);
			mapper2.insert(user);
		}
		session.commit();
		session.close();
		if (user != null) {
			throw new RuntimeException("测试异常");
		}
	}
	
}
