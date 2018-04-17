package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the bom_material database table.
 * 
 */
@Entity
@Table(name="bom_material")
@NamedQuery(name="BomMaterial.findAll", query="SELECT b FROM BomMaterial b")
public class BomMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mtl_code")
	private String mtlCode;

	private String brand;

	private String description;

	@Column(name="product_name")
	private String productName;

	private String specification;

	private Timestamp timestamp;

	public BomMaterial() {
	}

	public String getMtlCode() {
		return this.mtlCode;
	}

	public void setMtlCode(String mtlCode) {
		this.mtlCode = mtlCode;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}