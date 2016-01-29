package com.elim.learn.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 该类主要介绍实体类的一个配置。<br/>
 * 对于实体类我们需要在类上使用@Entity进行标注，表示其是一个实体类。<br/>
 * 使用@Table标注，用于配置实体类映射的数据库表信息，包括指定表名、索引等。
 *
 * @author elim
 *
 * @date 2016年1月29日 下午4:21:45
 *
 */
@Table(name = "t_entity_config", indexes = { @Index(name="idx_entity_config", columnList = "name,order_no", unique = false) }, uniqueConstraints = { @UniqueConstraint(columnNames = { "no" }) })
@Entity
public class EntityConfig {

	private Integer id;
	private String no;
	private String name;
	private Integer orderNo;

	@Id
	@GeneratedValue
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
	 * 注解@Column用于定义字段信息，如不指定则将使用默认的
	 * @return
	 */
	@Column(name="order_no", nullable=false)
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

}
