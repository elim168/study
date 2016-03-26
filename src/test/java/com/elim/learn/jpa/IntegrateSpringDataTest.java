package com.elim.learn.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.jpa.spring.data.dao.OrganizationRepository;
import com.elim.learn.jpa.spring.data.entity.Organization;
import com.elim.learn.jpa.spring.data.service.OrganizationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class IntegrateSpringDataTest {

	@Autowired
	private OrganizationRepository orgRepository;
	
	@Autowired
	private OrganizationService orgService;
	
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
	public void testSaveIterator() {
		Collection<Organization> orgList = new ArrayList<>();
		Organization org = null;
		for (int i=0; i<20; i++) {
			org = new Organization();
			org.setName("Org_" + (i+1));
			org.setOrder(i+1);
			org.setNo("N" + (i+1));
			orgList.add(org);
		}
		orgRepository.save(orgList);
	}
	
	@Test
	public void testFindOne() {
		//findOne()返回的是对应对象的实体
		orgRepository.findOne(1);
		//getOne()返回的是一个对应对象的代理对象
		orgRepository.getOne(1);
	}
	
	/**
	 * JpaRepository的saveAndFlush()方法相当于EntityManager的merge方法，可用于新增和更新。
	 */
	@Test
	public void testSaveAndFlush() {
//		Organization org = orgRepository.findOne(2);
		Organization org = new Organization();
		org.setId(1);
		org.setNo("B001");
		orgRepository.saveAndFlush(org);
	}
	
	@Test
	public void test1() {
		String name = "Org1";
		System.out.println(orgRepository.findByName(name));
		System.out.println(orgRepository.getByName(name));
		System.out.println(orgRepository.readByName(name));
	}
	
	@Test
	public void test2() {
		List<Organization> orgs = orgRepository.findByNameAndOrderGreaterThan("Org_10", 8);
		System.out.println(orgs);
	}
	
	@Test
	public void test3() {
		System.out.println(orgRepository.findByNameLike("Org%"));;
	}
	
	@Test
	public void test4() {
		System.out.println(orgRepository.findByNameIgnoreCase("org1"));
	}
	
	@Test
	public void test5() {
		System.out.println(orgRepository.queryDistinctByName("Org1"));
	}
	
	@Test
	public void test6() {
		System.out.println(orgRepository.queryNameByOrderGreaterThan(8));
	}
	
	@Test
	public void test7() {
		orgService.updateNo("N40", 40);
	}
	
	@Test
	public void test8() {
		String no = "N40";
		System.out.println(orgRepository.findByOrgNo(no));
		System.out.println(orgRepository.findByOrgNo2(no));
	}
	
	@Test
	public void test9() {
		System.out.println(orgRepository.findNoById(8));
	}
	
	@Test
	public void test10() {
		System.out.println(orgRepository.countByName("Org1"));
	}
	
}
