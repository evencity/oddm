package com.apical.oddm.core.model.bom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bom_unread")
public class BomUnread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="bom_id")
	private Integer bomId;

	@Id
	@Column(name="user_id")
	private Integer userId;

	public Integer getBomId() {
		return bomId;
	}

	public void setBomId(Integer bomId) {
		this.bomId = bomId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BomUnread(Integer userId, Integer bomId) {
		this.userId = userId;
		this.bomId = bomId;
	}

	public BomUnread(String userId, Integer bomId) {
		this.userId = Integer.parseInt(userId);
		this.bomId = bomId;
	}
	
	public BomUnread() {
	}

	@Override
	public String toString() {
		return "BomUnread [bomId=" + bomId + ", userId=" + userId + "]";
	}

}
