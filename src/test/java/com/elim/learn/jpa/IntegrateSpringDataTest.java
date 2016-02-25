package com.elim.learn.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.jpa.spring.data.dao.OrganizationRepository;
import com.elim.learn.jpa.spring.data.entity.Organization;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class IntegrateSpringDataTest {

	@Autowired
	private OrganizationRepository orgRepository;
	
	/**
	 * JpaRepository的save()相当于JPA里面EntityManager的persist方法。
	 */
	@Test
	public void testSave() {
		Organization org = new Organization();
		org.setNo("A001");
		org.setName("Org1");
		orgRepository.save(org);
	}
	
	@Test
	public void testFindOne() {
		//findOne()返回的是对应对象的实体
		Organization org1 = orgRepository.findOne(1);
		//getOne()返回的是一个对应对象的代理对象
		Organization org2 = orgRepository.getOne(1);
	}
	
	/**
	 * JpaRepository的saveAndFlush()方法相当于EntityManager的merge方法，可用于新增和更新。
	 */
	@Test
	public void testSaveAndFlush() {
		Organization org = orgRepository.findOne(2);
		org.setNo("B001");
		orgRepository.saveAndFlush(org);
	}
	
}
