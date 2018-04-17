package com.apical.oddm.facade.order.dto;

import java.io.Serializable;


/**
 * The persistent class for the order_altinfo database table.
 * 
 */
public class OrderInfoAltDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String afterInfo;

	private String alteritem;

	private String beforeInfo;

	private String operator;

	private Integer operateType;
	
	private String timestamp;

	private String orderId;

	

	@Override
	public String toString() {
		return "OrderInfoAltDTO [id=" + id + ", afterInfo=" + afterInfo + ", alteritem=" + alteritem + ", beforeInfo=" + beforeInfo + ", operator=" + operator + ", operateType="
				+ operateType + ", timestamp=" + timestamp + ", orderId=" + orderId + "]";
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}