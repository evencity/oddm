package com.apical.oddm.facade.order.command;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the order_altinfo database table.
 * 
 */
public class OrderInfoAltCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String afterInfo;//变更后

	private String alteritem;//变更项

	private String beforeInfo;//变更前

	private String operator;//操作人

	private Timestamp timestamp;//变更时间

	private Integer orderId;

	@Override
	public String toString() {
		return "OrderInfoAltCommand [id=" + id + ", afterInfo=" + afterInfo + ", alteritem=" + alteritem + ", beforeInfo=" + beforeInfo + ", operator=" + operator + ", timestamp="
				+ timestamp + ", orderId=" + orderId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAfterInfo() {
		return afterInfo;
	}

	public void setAfterInfo(String afterInfo) {
		this.afterInfo = afterInfo;
	}

	public String getAlteritem() {
		return alteritem;
	}

	public void setAlteritem(String alteritem) {
		this.alteritem = alteritem;
	}

	public String getBeforeInfo() {
		return beforeInfo;
	}

	public void setBeforeInfo(String beforeInfo) {
		this.beforeInfo = beforeInfo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}