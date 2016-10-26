package com.elim.learn.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 钥匙类
 *
 * @author elim
 *
 * @date 2016年1月5日 下午10:56:26
 *
 */
@Table(name="t_key")
@Entity
public class Key {

	private Integer id;
	private String no;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="key_no", length=20)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

}
