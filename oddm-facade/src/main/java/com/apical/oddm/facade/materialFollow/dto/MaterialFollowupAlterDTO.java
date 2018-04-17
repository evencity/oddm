package com.apical.oddm.facade.materialFollow.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class MaterialFollowupAlterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String afterInfo;

	private String alteritem;

	private String beforeInfo;

	private String operator;

	private Integer operateType;
	
	private String timestamp;

	private MaterialFollowupDTO materialFollowupDTO;

	public MaterialFollowupAlterDTO() {
	}

	public MaterialFollowupAlterDTO(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getAfterInfo() {
		return this.afterInfo;
	}

	public void setAfterInfo(String afterInfo) {
		this.afterInfo = afterInfo;
	}

	public String getAlteritem() {
		return this.alteritem;
	}

	public void setAlteritem(String alteritem) {
		this.alteritem = alteritem;
	}

	public String getBeforeInfo() {
		return this.beforeInfo;
	}

	public void setBeforeInfo(String beforeInfo) {
		this.beforeInfo = beforeInfo;
	}

	public String getOperator() {
		return this.operator;
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

	public MaterialFollowupDTO getMaterialFollowupDTO() {
		return materialFollowupDTO;
	}

	public void setMaterialFollowupDTO(MaterialFollowupDTO materialFollowupDTO) {
		this.materialFollowupDTO = materialFollowupDTO;
	}

	@Override
	public String toString() {
		return "MaterialFollowupAlterCommand [id=" + id + ", afterInfo=" + afterInfo + ", alteritem=" + alteritem + ", beforeInfo=" + beforeInfo + ", operator=" + operator
				+ ", operateType=" + operateType + ", timestamp=" + timestamp + ", materialFollowupDTO=" + materialFollowupDTO + "]";
	}

}