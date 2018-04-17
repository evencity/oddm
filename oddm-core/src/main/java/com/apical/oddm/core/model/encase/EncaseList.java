package com.apical.oddm.core.model.encase;

import java.io.Serializable;

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
 * The persistent class for the packing_list database table.
 * 
 */
@Entity
@Table(name="encase_list")
@NamedQuery(name="EncaseList.findAll", query="SELECT p FROM EncaseList p")
public class EncaseList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="c_no")
	private String cNo;

	private String description;

	@Column(name="g_w")
	private Integer gW;

	private Integer hight;

	@Column(name="item_no")
	private String itemNo;

	private Integer length;

	@Column(name="n_w")
	private Integer nW;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="product_fatory")
	private String productFatory;

	private Integer qty;

	@Column(name="qty_ctn")
	private Integer qtyCtn;

	private String remark;

	private String unit;

	private Integer width;

	//bi-directional many-to-one association to PackingInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="encase_infoid")
	private EncaseInfo encaseInfo;

	public EncaseList() {
	}

	public EncaseList(Integer id) {
		this.id = id;
	}
	
	public EncaseInfo getEncaseInfo() {
		return encaseInfo;
	}

	public void setEncaseInfo(EncaseInfo encaseInfo) {
		this.encaseInfo = encaseInfo;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCNo() {
		return this.cNo;
	}

	public void setCNo(String cNo) {
		this.cNo = cNo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGW() {
		return this.gW;
	}

	public void setGW(Integer gW) {
		this.gW = gW;
	}

	public Integer getHight() {
		return this.hight;
	}

	public void setHight(Integer hight) {
		this.hight = hight;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getNW() {
		return this.nW;
	}

	public void setNW(Integer nW) {
		this.nW = nW;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductFatory() {
		return this.productFatory;
	}

	public void setProductFatory(String productFatory) {
		this.productFatory = productFatory;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getQtyCtn() {
		return this.qtyCtn;
	}

	public void setQtyCtn(Integer qtyCtn) {
		this.qtyCtn = qtyCtn;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

}