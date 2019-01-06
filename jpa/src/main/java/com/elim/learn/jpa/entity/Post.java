package com.elim.learn.jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 岗位类，一个人可以有多个岗位，一个岗位也可以有多个员工。岗位和员工是多对多的关系
 *
 * @author elim
 *
 * @date 2016年1月10日 下午8:37:04
 *
 */
@Table(name = "t_post")
@Entity
public class Post {

	private Integer id;
	private String name;
	private Set<Employee> employees = new HashSet<>();

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Post这方不维护关联关系，通过@ManyToMany的mappedBy属性指定维护关联关系那方实体对象对应的属性。
	 * @return
	 */
	@ManyToMany(mappedBy="posts")
	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

}
