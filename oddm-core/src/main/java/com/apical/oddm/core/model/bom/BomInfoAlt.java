package com.apical.oddm.core.model.bom;

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
 * The persistent class for the bom_info_alter database table.
 * 
 */
@Entity
@Table(name="bom_info_alter")
@NamedQuery(name="BomInfoAlt.findAll", query="SELECT o FROM BomInfoAlt o")
public class BomInfoAlt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="after_info")
	private String afterInfo;

	private String alteritem;

	/*@Column(name="alteritem_desc")
	private String altitemDesc;*/

	@Column(name="before_info")
	private String beforeInfo;

	private String operator;

	@Column(name="operate_type")
	private Integer operateType;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	//bi-directional many-to-one association to BomInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bominfoid")
	private BomInfo bomInfo;

	public BomInfoAlt() {
	}

	public BomInfoAlt(Integer id) {
		this.id = id;
	}
	
	public BomInfoAlt(BomInfo bomInfo, String userName) {
		this.bomInfo = bomInfo;
		this.operator = userName;
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

	public BomInfo getBomInfo() {
		return bomInfo;
	}

	public void setBomInfo(BomInfo bomInfo) {
		this.bomInfo = bomInfo;
	}

}