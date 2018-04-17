package com.apical.oddm.facade.bom.dto;

import java.io.Serializable;
/**
 *  BOM变更记录
 * @author 
 *
 */
public class BomInfoAltDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String afterInfo;

	private String alteritem;

	private String altitemDesc;

	private String beforeInfo;

	private String operator;

	private String type;

	private String timestamp;
	
	private Integer operateType;

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

	public String getAltitemDesc() {
		return altitemDesc;
	}

	public void setAltitemDesc(String altitemDesc) {
		this.altitemDesc = altitemDesc;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

}
