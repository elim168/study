package com.elim.learn.jpa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 会议室
 *
 * @author elim
 *
 * @date 2016年1月10日 下午6:15:26
 *
 */
@Table(name="t_meeting_room")
@Entity
public class MeetingRoom {

	private Integer id;
	private String no;
	private Projector projector;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="room_no", length=20)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * 对于单向的一对一关联关系，其类似于单向的多对一关联关系，可以使用@JoinColumn的name属性指定对应的外键列名称。<br/>
	 * 如果是双向的一对一关联，则可以在不需要维护关联关系的另一方通过@OneToOne的mappedBy属性指定需要维护关联关系那一方维护关系时对应的属性名。
	 * @return
	 */
	@JoinColumn(name="projector_id")
	@OneToOne(cascade=CascadeType.ALL)
	public Projector getProjector() {
		return projector;
	}

	public void setProjector(Projector projector) {
		this.projector = projector;
	}

}
