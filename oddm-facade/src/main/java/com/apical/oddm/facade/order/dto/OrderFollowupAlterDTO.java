package com.apical.oddm.facade.order.dto;

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
public class OrderFollowupAlterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String operator;
	
	private Integer operateType;

	private String timestamp;

	private String afterInfo;

	private String alteritem;

	private String beforeInfo;
	
	private String orderFollowupId;

	
	@Override
	public String toString() {
		return "OrderFollowupAlterDTO [id=" + id + ", operator=" + operator + ", operateType=" + operateType + ", timestamp=" + timestamp + ", afterInfo=" + afterInfo
				+ ", alteritem=" + alteritem + ", beforeInfo=" + beforeInfo + ", orderFollowupId=" + orderFollowupId + "]";
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
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