package com.apical.oddm.core.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order_unread")
public class OrderUnread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id")
	private Integer orderId;

	@Id
	@Column(name="user_id")
	private Integer userId;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public OrderUnread(Integer userId, Integer orderId) {
		this.userId = userId;
		this.orderId = orderId;
	}

	public OrderUnread(String userId, Integer orderId) {
		this.userId = Integer.parseInt(userId);
		this.orderId = orderId;
	}
	
	public OrderUnread() {
	}

	@Override
	public String toString() {
		return "OrderUnread [orderId=" + orderId + ", userId=" + userId + "]";
	}

}
