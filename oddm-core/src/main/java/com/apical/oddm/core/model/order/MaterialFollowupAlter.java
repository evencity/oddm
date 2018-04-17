package com.apical.oddm.core.model.order;

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
 * The persistent class for the order_mt_followup_alter database table.
 * 
 */
@Entity
@Table(name="order_mt_followup_alter")
@NamedQuery(name="MaterialFollowupAlter.findAll", query="SELECT o FROM MaterialFollowupAlter o")
public class MaterialFollowupAlter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="after_info")
	private String afterInfo;

	private String alteritem;

	@Column(name="before_info")
	private String beforeInfo;

	private String operator;

	@Column(name="operate_type")
	private Integer operateType;
	
	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	//bi-directional many-to-one association to OrderMtFollowup
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_mt_fllowupid")
	private MaterialFollowup materialFollowup;

	public MaterialFollowupAlter() {
	}

	public MaterialFollowupAlter(Integer id) {
		this.id = id;
	}
	
	public MaterialFollowupAlter(MaterialFollowup materialFollowup, String operator) {
		this.materialFollowup = materialFollowup;
		this.operator = operator;
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

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public MaterialFollowup getMaterialFollowup() {
		return materialFollowup;
	}

	public void setMaterialFollowup(MaterialFollowup materialFollowup) {
		this.materialFollowup = materialFollowup;
	}

}