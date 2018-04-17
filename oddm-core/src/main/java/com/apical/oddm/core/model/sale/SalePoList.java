package com.apical.oddm.core.model.sale;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * The persistent class for the sale_po_list database table.
 * 
 */
@Entity
@Table(name="sale_po_list")
@NamedQuery(name="SalePoList.findAll", query="SELECT s FROM SalePoList s")
public class SalePoList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String number;

	@Column(name="product_name")
	private String productName;

	private Integer quality;

	private String specification;

	@Column(name="unit_price")
	private BigDecimal unitPrice;

	//bi-directional many-to-one association to SalePo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="poid")
	private SalePo salePo;

	public SalePoList() {
	}

	public SalePoList(Integer id) {
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

	public SalePo getSalePo() {
		return this.salePo;
	}

	public void setSalePo(SalePo salePo) {
		this.salePo = salePo;
	}

}