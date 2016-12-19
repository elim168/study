/**
 * 
 */
package com.elim.learn.mybatis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.mybatis.dao.OrganizationBaseMapper;
import com.elim.learn.mybatis.dao.PersonMapper;
import com.elim.learn.mybatis.model.Organization;
import com.elim.learn.mybatis.model.OrganizationBase;
import com.elim.learn.mybatis.model.Person;

/**
 * @author Elim
 * 2016年10月24日
 *
 */
@ContextConfiguration("classpath:/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OrgTest {

	@Autowired
	private OrganizationBaseMapper orgBaseMapper;
	@Autowired
	private PersonMapper personMapper;
	
	@Test
	public void testInsert() {
		Organization orgBase = new Organization();
		orgBase.setName("TEST_ORG");
		this.orgBaseMapper.insert(orgBase);
	}
	
	@Test
	public void testInsertPerson() {
		Person person = new Person();
		person.setName("ZhangSan");
		person.setEmail("zhangsan@163.com");
		person.setMobile("15889898989");
		person.setParentId(1);
		this.orgBaseMapper.insert(person);
		this.personMapper.insert(person);
	}
	
	@Test
	public void testFind() {
		OrganizationBase org = this.orgBaseMapper.findById(1L);
		System.out.println(org.getClass());
		System.out.println(org.getType());
		
		OrganizationBase person = this.orgBaseMapper.findById(4L);//Person
		System.out.println(person.getClass());
		System.out.println(((Person)person).getEmail());
	}
	
}
