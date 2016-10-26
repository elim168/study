package com.elim.learn.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 班级，双向一对多关联用例对应的一的一方。
 *
 * @author elim
 *
 * @date 2016年1月10日 上午11:54:36
 *
 */
@Table(name="t_class")
@Entity
public class Class {

	private Integer id;
	private String no;
	private String name;
	private List<Student> students = new ArrayList<Student>();

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 对于双向的一对多关系一般就由多的一方进行关系的维护，而在一的一端通过@OneToMany的mappedBy属性来指定多的一方维护关联关系的属性名，在这里其实就是ownClass。<br/>
	 * 当然了，对于这种双向的关联关系，我们也可以在一的一端通过@JoinColumn来指定维护关联关系的外键字段名，但是这样的话就是双向都需要维护关联关系，影响性能。<br/>
	 * 此外，mappedBy属性不能与@JoinColumn一起使用。
	 * @return
	 */
	@OneToMany(mappedBy="ownClass")
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
