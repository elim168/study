package com.elim.learn.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 锁，单向的一对多关联Key
 *
 * @author elim
 *
 * @date 2016年1月5日 下午10:56:39
 *
 */
@Table(name="t_lock")
@Entity
public class Lock {

	private Integer id;
	private String no;
	private List<Key> keys = new ArrayList<Key>();

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="lock_no", length=20)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @OneToMany注解用来指定一对多关联关系，在维护这种关系时也是通过在多的一方对应的表中存有一那方的主键，可以通过@JoinColumn注解的name属性来指定多的一方
	 * 对应表中关联一的那方的主键对应的字段名。
	 * 
	 */
	@JoinColumn(name="lock_id")
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH})
	public List<Key> getKeys() {
		return keys;
	}

	public void setKeys(List<Key> keys) {
		this.keys = keys;
	}

}
