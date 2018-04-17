package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the bom_info database table.
 * 
 */
@Entity
@Table(name="bom_info")
public class BomInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String brand;

	private String description;

	@Column(name="material_code")
	private String materialCode;

	@Column(name="product_name")
	private String productName;

	private String specification;

	private byte state;

	private Timestamp timestamp;

	private String title;

	private Timestamp updatetime;

	private String user;

	private String version;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	//bi-directional many-to-one association to BomMaterialRef
	@OneToMany(mappedBy="bomInfo")
	private Set<BomMaterialRef> bomMaterialRefs;

	public BomInfo() {
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

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Set<BomMaterialRef> getBomMaterialRefs() {
		return this.bomMaterialRefs;
	}

	public void setBomMaterialRefs(Set<BomMaterialRef> bomMaterialRefs) {
		this.bomMaterialRefs = bomMaterialRefs;
	}

	public BomMaterialRef addBomMaterialRef(BomMaterialRef bomMaterialRef) {
		getBomMaterialRefs().add(bomMaterialRef);
		bomMaterialRef.setBomInfo(this);

		return bomMaterialRef;
	}

	public BomMaterialRef removeBomMaterialRef(BomMaterialRef bomMaterialRef) {
		getBomMaterialRefs().remove(bomMaterialRef);
		bomMaterialRef.setBomInfo(null);

		return bomMaterialRef;
	}

}