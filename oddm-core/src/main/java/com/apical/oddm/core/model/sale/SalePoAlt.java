package com.apical.oddm.core.model.sale;

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

import com.apical.oddm.infra.util.OddmDateUtil;

@Entity
@Table(name="sale_po_altinfo")
@NamedQuery(name="SalePoAlt.findAll", query="SELECT o FROM SalePoAlt o")
public class SalePoAlt implements Serializable {
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

	//bi-directional many-to-one association to SalePo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="po_id")
	private SalePo salePo;

	public SalePoAlt() {
	}

	public SalePoAlt(Integer id) {
		this.id = id;
	}
	
	public SalePoAlt(SalePo salePo, String operator) {
		this.salePo = salePo;
		this.operator = operator;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
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
	
	public SalePo getSalePo() {
		return this.salePo;
	}

	public void setSalePo(SalePo SalePo) {
		this.salePo = SalePo;
	}

	@Override
	public String toString() {
		String operateTy = "";
		if (operateType == 1) {
			operateTy = "增加";
		} else if (operateType == 2) {
			operateTy = "删除";
		} else if (operateType == 3) {
			operateTy = "修改";
		} else {
			return "变更消息异常，请联系开发人员！";
		}
		beforeInfo = beforeInfo==null?"":beforeInfo;
		afterInfo = afterInfo==null?"":afterInfo;
		String result =  "用户‘"+operator+"’执行‘"+operateTy+"’操作;"
				+ "<br/>变更项 ["+ alteritem + "];"
				+ "<br/>变更前 [" + beforeInfo +"];"
				+ "<br/>变更后 [" + afterInfo + "];"
				+ "<br/>操作时间 ["+ OddmDateUtil.dateFmat.format(System.currentTimeMillis()) + "]";
		//System.err.println(result);
		return result;
	}
	//封装变更记录通过websocket消息发送至页面
		//private String altInfoToString(int operateType, String beforeInfo, String afterInfo, String operator, String alteritem) {
}