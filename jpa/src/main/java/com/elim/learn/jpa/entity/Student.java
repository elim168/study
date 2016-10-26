package com.elim.learn.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 学生，双向一对多关联中对应的多的一方
 *
 * @author elim
 *
 * @date 2016年1月10日 上午11:55:34
 *
 */
@Table(name="t_student")
@Entity
public class Student {

	private Integer id;
	private String no;
	private String name;
	private Class ownClass;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="student_no", length=20)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(length=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JoinColumn(name="class_id")
	@ManyToOne
	public Class getOwnClass() {
		return ownClass;
	}

	public void setOwnClass(Class ownClass) {
		this.ownClass = ownClass;
	}

}
