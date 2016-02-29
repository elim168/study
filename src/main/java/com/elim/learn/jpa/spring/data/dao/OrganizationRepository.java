package com.elim.learn.jpa.spring.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elim.learn.jpa.spring.data.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

	/**
	 * 根据名称查找Organization<br/>
	 * Spring data的查询规范，当方法名定义以find...By、read...By、query...By、get...By或count...By这样的形式，其后可以接查询条件的名称，有多个查询条件时可以使用And或Or等
	 * 关键字，名称中包含的属性关键字必须与方法参数的位置保持一致，保持这种规范的方法Spring Data将可以自动转换为对应的查询语句。如下查询就可以自动解析为：
	 * select id,name,no,org_order from t_organization a where a.name = ?这样的形式，如果希望SpringData自动为我们解析为
	 * “select id, name, no, org_order from t_organization a where a.name = ? and a.org_order > ?”则我们可以将方法名
	 * 定义为findByNameAndOrderGreaterThan(String name, Integer order)。
	 * @param name
	 * @return
	 */
	public List<Organization> findByName(String name);
	
	public List<Organization> readByName(String name);
	
	public List<Organization> getByName(String name);
	
	/**
	 * 根据name进行筛选后的记录数，形成的SQL如下：
	 *    select
		        count(organizati0_.id) as col_0_0_ 
		    from
		        t_organization organizati0_ 
		    where
		        organizati0_.name=?
	 * @param name
	 * @return
	 */
	public Long countByName(String name);
	
	/**
	 * 如果希望根据某一条件查询时不区分大小写，则可以在定义方法名时在这个属性后加上IgnoreCase。这通常是在数据库层面把它都转换为大写。如：
	 *     select
		        organizati0_.id as id1_8_,
		        organizati0_.name as name2_8_,
		        organizati0_.no as no3_8_,
		        organizati0_.org_order as org_orde4_8_ 
		    from
		        t_organization organizati0_ 
		    where
		        upper(organizati0_.name)=upper(?)
	 * @param name
	 * @return
	 */
	public List<Organization> findByNameIgnoreCase(String name);
	
	/**
	 * 有的时候我们的SQL中是包含多个查询条件的，如果希望所有的条件都不区分大小写，则可以在最后方法名的最后加上AllIgnoreCase
	 * @param name
	 * @param no
	 * @return
	 */
	public List<Organization> findByNameAndNoAllIgnoreCase(String name, String no);
	
	/**
	 * 还可以使用Distinct这样的关键字。这样生成出来的SQL是这样的形式：
	 *     select
		        distinct organizati0_.id as id1_8_,
		        organizati0_.name as name2_8_,
		        organizati0_.no as no3_8_,
		        organizati0_.org_order as org_orde4_8_ 
		    from
		        t_organization organizati0_ 
		    where
		        organizati0_.name=?
	 * @param name
	 * @return
	 */
	public List<Organization> queryDistinctByName(String name);
	
	/**
	 * 如果希望只查询对象中的一个属性，则不可以使用SpringData的这种根据方法名来自动解析SQL的形式，此时我们就可以@Query
	 * 注解来手动指定查询的SQL语句
	 * @param order
	 * @return
	 */
	@Query("select a.name from Organization a where a.order > ?1")
	public List<String> queryNameByOrderGreaterThan(Integer order);
	
	/**
	 * 查找指定名称和排序号大于指定排序号的Organization
	 * @param name
	 * @param order
	 * @return
	 */
	public List<Organization> findByNameAndOrderGreaterThan(String name, Integer order);
	
	/**
	 * 查找名称like指定名称的。SpringData支持的关键字有：
		And
		Or
		After, IsAfter
		Before, IsBefore
		Containing, IsContaining, Contains
		Between, IsBetween
		EndingWith, IsEndingWith, EndsWith
		Exists
		False, IsFalse
		GreaterThan, IsGreaterThan
		GreaterThanEqual, IsGreaterThanEqual
		In, IsIn
		Is, Equals, (or no keyword)
		NotNull, IsNotNull
		Null, IsNull
		LessThan, IsLessThan
		LessThanEqual, IsLessThanEqual
		Like, IsLike
		Near, IsNear
		Not, IsNot
		NotIn, IsNotIn
		NotLike, IsNotLike
		Regex, MatchesRegex, Matches
		StartingWith, IsStartingWith, StartsWith
		True, IsTrue
		Within, IsWithin
	 * @param name
	 * @return
	 */
	public List<Organization> findByNameLike(String name);
	
	/**
	 * 除了采用SpringData规范的方法名使SpringData能够自动解析为对应的SQL外，对于一些复杂的情况采用规范的方法名可能就不那么方便了，
	 * 这个时候我们就可以使用SpringData为我们提供的@Query注解来指定需要使用的查询语句，SpringData使用的查询语句定义参数时，有两种方式，
	 * 一种是采用？+数字的形式，数字表示方法的第几个参数；第二种形式是采用命名参数的方式，形式如“:name”，这个时候我们 就可以通过给方法参数使用@Param
	 * 注解进行标注的形式来指定方法参数需要绑定的SQL中的参数。
	 * @param no
	 * @return
	 */
	@Query("from Organization a where a.no = ?1")
	public List<Organization> findByOrgNo(String no);
	
	@Query("from Organization a where a.no = :no")
	public List<Organization> findByOrgNo2(@Param("no") String no);
	
	/**
	 * 如果@Query定义的是DML语句，则需要同时使用@Modifying进行标注
	 * @param no
	 * @param id
	 */
	@Modifying
	@Query("update Organization a set a.no = ?1 where a.id = ?2")
	public void updateNo(String no, Integer id);
	
	/**
	 * Query注解也可以用来指定数据库本地查询SQL语句，此时我们需要指定其nativeQuery属性为true
	 * @param id
	 * @return
	 */
	@Query(value="select no from t_organization a where a.id=?1", nativeQuery=true)
	public String findNoById(Integer id);
	
}
