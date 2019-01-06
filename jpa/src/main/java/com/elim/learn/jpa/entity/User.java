package com.elim.learn.jpa.entity;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Cacheable
@NamedQuery(name="queryName", query="from User u where u.age < ?")
@NamedQueries({
	@NamedQuery(name="query1", query="from User u where u.name like ?"),
	@NamedQuery(name="query2", query="from User u where u.name = ?")
	})
@Entity
@Table(name="t_user")
public class User {

	private Integer id;
	private String name;
	private Integer age;
	private Date birthday;
	private Date createdDate = new Date();

	public User() {
		
	}
	
	public User(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Column注解用于指定普通属性对应的字段信息，如果是对象关联这样的将使用@JoinColumn注解来指定字段信息
	 * @return
	 */
	@Column(name="user_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @Temporal用于标注在java.util.Date类型和java.util.Calendar类型上来指定日期类型，可选值有TemporalType.DATE、TIME和TIMESTAMP
	 * @return
	 */
	@Temporal(TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age
				+ ", birthday=" + birthday + ", createdDate=" + createdDate
				+ "]";
	}
	
}
