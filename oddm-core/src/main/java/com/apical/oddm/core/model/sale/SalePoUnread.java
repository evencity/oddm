package com.apical.oddm.core.model.sale;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sale_po_unread")
public class SalePoUnread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="po_id")
	private Integer poId;

	@Id
	@Column(name="user_id")
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SalePoUnread(Integer userId, Integer poId) {
		this.userId = userId;
		this.poId = poId;
	}

	public SalePoUnread(String userId, Integer orderId) {
		this.userId = Integer.parseInt(userId);
		this.poId = orderId;
	}
	
	public SalePoUnread() {
	}

	public Integer getPoId() {
		return poId;
	}

	public void setPoId(Integer poId) {
		this.poId = poId;
	}

	@Override
	public String toString() {
		return "SalePoUnread [poId=" + poId + ", userId=" + userId + "]";
	}
	
}
