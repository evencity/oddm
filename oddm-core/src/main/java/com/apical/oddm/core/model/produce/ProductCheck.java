package com.apical.oddm.core.model.produce;

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

import com.apical.oddm.core.model.material.ProductType;


/**
 * The persistent class for the product_check database table.
 * 
 */
@Entity
@Table(name="product_check")
@NamedQuery(name="ProductCheck.findAll", query="SELECT p FROM ProductCheck p")
@Deprecated
public class ProductCheck implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String method;

	private String name;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type")
	private ProductType productType;
	
	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	public ProductCheck() {
	}

	public ProductCheck(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}