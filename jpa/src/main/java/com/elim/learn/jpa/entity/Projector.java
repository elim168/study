package com.elim.learn.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 投影仪
 *
 * @author elim
 *
 * @date 2016年1月10日 下午6:16:16
 *
 */
@Table(name = "t_projector")
@Entity
public class Projector {

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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

}
