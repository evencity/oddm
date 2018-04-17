package com.apical.oddm.facade.order.command;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the order_followup_alter database table.
 * 
 */
public class OrderFollowupAlterCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String operator;//操作人

	private Timestamp timestamp;//变更时间

	private String afterInfo;//变更前

	private String alteritem;//变更项

	private String beforeInfo;//变更后
	
	private String orderFollowupId;

	@Override
	public String toString() {
		return "OrderFollowupAlter [id=" + id + ", operator=" + operator + ", timestamp=" + timestamp + ", afterInfo=" + afterInfo + ", alteritem=" + alteritem + ", beforeInfo="
				+ beforeInfo + ", orderFollowupId=" + orderFollowupId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getOrderFollowupId() {
		return orderFollowupId;
	}

	public void setOrderFollowupId(String orderFollowupId) {
		this.orderFollowupId = orderFollowupId;
	}
	
}