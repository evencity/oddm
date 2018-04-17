package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the packing_list database table.
 * 
 */
@Entity
@Table(name="packing_list")
@NamedQuery(name="PackingList.findAll", query="SELECT p FROM PackingList p")
public class PackingList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="c_no")
	private String cNo;

	private String description;

	@Column(name="g_w")
	private double gW;

	private short hight;

	@Column(name="item_no")
	private String itemNo;

	private short length;

	@Column(name="n_w")
	private double nW;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="product_fatory")
	private String productFatory;

	private int qty;

	@Column(name="qty_ctn")
	private int qtyCtn;

	private String remark;

	private String unit;

	private short width;

	//bi-directional many-to-one association to PackingInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="packing_infoid")
	private PackingInfo packingInfo;

	public PackingList() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public double getGW() {
		return this.gW;
	}

	public void setGW(double gW) {
		this.gW = gW;
	}

	public short getHight() {
		return this.hight;
	}

	public void setHight(short hight) {
		this.hight = hight;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public short getLength() {
		return this.length;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public double getNW() {
		return this.nW;
	}

	public void setNW(double nW) {
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

	public int getQty() {
		return this.qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getQtyCtn() {
		return this.qtyCtn;
	}

	public void setQtyCtn(int qtyCtn) {
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

	public short getWidth() {
		return this.width;
	}

	public void setWidth(short width) {
		this.width = width;
	}

	public PackingInfo getPackingInfo() {
		return this.packingInfo;
	}

	public void setPackingInfo(PackingInfo packingInfo) {
		this.packingInfo = packingInfo;
	}

}