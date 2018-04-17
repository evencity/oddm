package com.apical.oddm.core.model.produce;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order_mft_unread")
public class ManufactureUnread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="mft_id")
	private Integer mftId;

	@Id
	@Column(name="user_id")
	private Integer userId;

	public Integer getMftId() {
		return mftId;
	}

	public void setMftId(Integer mftId) {
		this.mftId = mftId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ManufactureUnread(Integer userId, Integer mftId) {
		this.userId = userId;
		this.mftId = mftId;
	}

	public ManufactureUnread(String userId, Integer mftId) {
		this.userId = Integer.parseInt(userId);
		this.mftId = mftId;
	}
	
	public ManufactureUnread() {
	}

	@Override
	public String toString() {
		return "ManufactureUnread [mftId=" + mftId + ", userId=" + userId + "]";
	}

}
