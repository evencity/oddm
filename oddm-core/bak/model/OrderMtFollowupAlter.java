package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the order_mt_followup_alter database table.
 * 
 */
@Entity
@Table(name="order_mt_followup_alter")
@NamedQuery(name="OrderMtFollowupAlter.findAll", query="SELECT o FROM OrderMtFollowupAlter o")
public class OrderMtFollowupAlter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@Column(name="after_info")
	private String afterInfo;

	private String alteritem;

	@Column(name="before_info")
	private String beforeInfo;

	private String operator;

	private Timestamp timestamp;

	//bi-directional many-to-one association to OrderMtFollowup
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_mt_fllowupid")
	private OrderMtFollowup orderMtFollowup;

	public OrderMtFollowupAlter() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

	public OrderMtFollowup getOrderMtFollowup() {
		return this.orderMtFollowup;
	}

	public void setOrderMtFollowup(OrderMtFollowup orderMtFollowup) {
		this.orderMtFollowup = orderMtFollowup;
	}

}