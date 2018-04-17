package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the order_mft_material database table.
 * 
 */
@Entity
@Table(name="order_mft_material")
@NamedQuery(name="OrderMftMaterial.findAll", query="SELECT o FROM OrderMftMaterial o")
public class OrderMftMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String brand;

	private String description;

	@Column(name="material_code")
	private String materialCode;

	private short orderby;

	@Column(name="product_name")
	private String productName;

	private String specification;

	private byte type;

	private Timestamp updatetime;

	@Column(name="usage_amount")
	private String usageAmount;

	//bi-directional many-to-one association to OrderManufacture
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_mftid")
	private OrderManufacture orderManufacture;

	public OrderMftMaterial() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public short getOrderby() {
		return this.orderby;
	}

	public void setOrderby(short orderby) {
		this.orderby = orderby;
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

	public byte getType() {
		return this.type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getUsageAmount() {
		return this.usageAmount;
	}

	public void setUsageAmount(String usageAmount) {
		this.usageAmount = usageAmount;
	}

	public OrderManufacture getOrderManufacture() {
		return this.orderManufacture;
	}

	public void setOrderManufacture(OrderManufacture orderManufacture) {
		this.orderManufacture = orderManufacture;
	}

}