package com.apical.oddm.facade.encase.dto;

import java.io.Serializable;

/**
 * 集装箱列表
 * @author wangtianqun
 *
 */
public class EncaseListDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String CNo;

	private String description;

	private Integer gW;

	private Integer hight;

	private String itemNo;

	private Integer length;

	private Integer nW;

	private String orderNo;

	private String productFatory;

	private Integer qty;

	private Integer qtyCtn;

	private String remark;

	private String unit;

	private Integer width;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCNo() {
		return CNo;
	}

	public void setCNo(String CNo) {
		this.CNo = CNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getgW() {
		return gW;
	}

	public void setgW(Integer gW) {
		this.gW = gW;
	}

	public Integer getHight() {
		return hight;
	}

	public void setHight(Integer hight) {
		this.hight = hight;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getnW() {
		return nW;
	}

	public void setnW(Integer nW) {
		this.nW = nW;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductFatory() {
		return productFatory;
	}

	public void setProductFatory(String productFatory) {
		this.productFatory = productFatory;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getQtyCtn() {
		return qtyCtn;
	}

	public void setQtyCtn(Integer qtyCtn) {
		this.qtyCtn = qtyCtn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}
	
	
}
