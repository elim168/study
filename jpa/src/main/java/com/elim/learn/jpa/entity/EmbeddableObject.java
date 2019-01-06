package com.elim.learn.jpa.entity;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * 使用@Embeddable进行标注的类可以作为持久化类中的一个普通属性，然后在持久化时会将其中的可持久化的属性作为
 *
 * @author elim
 *
 * @date 2016年2月1日 上午9:53:26
 *
 */
@Embeddable
public class EmbeddableObject {

	private String prop1;
	private Integer prop2;
	private String nonPersistProp;

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public Integer getProp2() {
		return prop2;
	}

	public void setProp2(Integer prop2) {
		this.prop2 = prop2;
	}

	@Transient
	public String getNonPersistProp() {
		return nonPersistProp;
	}

	public void setNonPersistProp(String nonPersistProp) {
		this.nonPersistProp = nonPersistProp;
	}

}
