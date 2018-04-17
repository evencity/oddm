package com.apical.oddm.facade.sale.po.cmd;

import java.math.BigDecimal;

import org.springframework.context.annotation.Description;

public class SalePoListCmd {

	private Integer id;

	private String description;

	private String number;

	private String productName;

	private Integer quality;

	private String specification;

	private BigDecimal unitPrice;

	private SalePoCmd salePoCmd;

	public SalePoListCmd() {
	}

	public SalePoListCmd(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Description(value="备注")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(value="NO.")
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Description(value="产品名称")
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Description(value="数量")
	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	@Description(value="型号规格")
	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Description(value="单价")
	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public SalePoCmd getSalePoCmd() {
		return salePoCmd;
	}

	public void setSalePoCmd(SalePoCmd salePoCmd) {
		this.salePoCmd = salePoCmd;
	}

	@Override
	public String toString() {
		return "SalePoListCmd [id=" + id + ", description=" + description
				+ ", number=" + number + ", productName=" + productName
				+ ", quality=" + quality + ", specification=" + specification
				+ ", unitPrice=" + unitPrice + ", salePoCmd=" + salePoCmd + "\n]";
	}
}