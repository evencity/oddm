package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the bom_material_ref database table.
 * 
 */
@Entity
@Table(name="bom_material_ref")
@NamedQuery(name="BomMaterialRef.findAll", query="SELECT b FROM BomMaterialRef b")
public class BomMaterialRef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String brand;

	private int contacts;

	private String description;

	@Column(name="material_code")
	private String materialCode;

	private short orderby;

	@Column(name="product_name")
	private String productName;

	private String specification;

	private byte type;

	private Timestamp updatetime;

	@Column(name="usage_amount1")
	private int usageAmount1;

	@Column(name="usage_amount2")
	private int usageAmount2;

	//bi-directional many-to-one association to BomInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bom_infoid")
	private BomInfo bomInfo;

	public BomMaterialRef() {
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

	public int getContacts() {
		return this.contacts;
	}

	public void setContacts(int contacts) {
		this.contacts = contacts;
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

	public int getUsageAmount1() {
		return this.usageAmount1;
	}

	public void setUsageAmount1(int usageAmount1) {
		this.usageAmount1 = usageAmount1;
	}

	public int getUsageAmount2() {
		return this.usageAmount2;
	}

	public void setUsageAmount2(int usageAmount2) {
		this.usageAmount2 = usageAmount2;
	}

	public BomInfo getBomInfo() {
		return this.bomInfo;
	}

	public void setBomInfo(BomInfo bomInfo) {
		this.bomInfo = bomInfo;
	}

}