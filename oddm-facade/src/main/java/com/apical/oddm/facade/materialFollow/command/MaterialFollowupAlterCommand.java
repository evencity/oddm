package com.apical.oddm.facade.materialFollow.command;

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

import com.apical.oddm.facade.materialFollow.dto.MaterialFollowupDTO;


public class MaterialFollowupAlterCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String afterInfo;

	private String alteritem;

	private String beforeInfo;

	private String operator;

	private Integer operateType;
	
	private Timestamp timestamp;
	
	private Integer materialFollowupId;

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

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getMaterialFollowupId() {
		return materialFollowupId;
	}

	public void setMaterialFollowupId(Integer materialFollowupId) {
		this.materialFollowupId = materialFollowupId;
	}

	@Override
	public String toString() {
		return "MaterialFollowupAlterCommand [id=" + id + ", afterInfo=" + afterInfo + ", alteritem=" + alteritem + ", beforeInfo=" + beforeInfo + ", operator=" + operator
				+ ", operateType=" + operateType + ", timestamp=" + timestamp + ", materialFollowupId=" + materialFollowupId + "]";
	}

}