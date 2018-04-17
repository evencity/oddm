package com.apical.oddm.facade.sale.po.dto;

import java.math.BigDecimal;

public class SalePoListDto {

	private Integer id;

	private String description;

	private String number;

	private String productName;

	private Integer quality;

	private String specification;

	private BigDecimal unitPrice;

	private SalePoDto salePo;

	public SalePoListDto() {
	}

	public SalePoListDto(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public SalePoDto getSalePo() {
		return this.salePo;
	}

	public void setSalePo(SalePoDto salePo) {
		this.salePo = salePo;
	}

}