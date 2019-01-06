package com.elim.learn.jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 员工类，一个人可以有多个岗位，一个岗位也可以有多个人。员工和岗位是多对多的关系
 *
 * @author elim
 *
 * @date 2016年1月10日 下午8:37:44
 *
 */
@Table(name = "t_employee")
@Entity
public class Employee {

	private Integer id;
	private String name;
	private Set<Post> posts = new HashSet<>();

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
	 * 对于双向的多对多关联关系需要有一个中间表来维护关联关系。在JPA中可以通过@JoinTable来指定中间表信息。<br/>
	 * <ul>
	 * 		<li>可以通过@JoinTable的name属性来指定中间表的名称</li>
	 * 		<li>
	 * 			可以通过@JoinTable的joinColumns属性来指定本实体的主键对应于中间表中的字段的名字，通过@JoinColumn的name属性指定关联中间表的字段名，通过
	 * 			referencedColumnName属性来指定中间表的对应字段关联于本实体对应表的哪个字段，如果本实体对应的表只有一个主键则可以忽略本属性。
	 * 		</li>
	 * 		<li>可以通过@JoinTable的inverseJoinColumns来指定关联的对象的主键对应于中间表的字段的名字</li>
	 * </ul>
	 * @return
	 */
	@JoinTable(name="t_employee_post", joinColumns=@JoinColumn(name="employee_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="post_id"))
	@ManyToMany(cascade=CascadeType.PERSIST)
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

}
