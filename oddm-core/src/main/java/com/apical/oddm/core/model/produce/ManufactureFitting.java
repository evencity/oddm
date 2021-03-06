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


/**
 * The persistent class for the order_mft_fitting database table.
 * 
 */
@Entity
@Table(name="order_mft_fitting")
@NamedQuery(name="ManufactureFitting.findAll", query="SELECT o FROM ManufactureFitting o")
public class ManufactureFitting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String label;

	private String name;

	@Column(name="pack_bag")
	private String packBag;

	@Column(name="pict_paths")
	private String pictPaths;

	@Column(name="pict_attribute")
	private String pictAttribute;
	
	private String specification;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	//bi-directional many-to-one association to OrderManufacture
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_mftid")
	private Manufacture orderManufacture;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="order_mftid", updatable=false, insertable=false)
	private Integer mftid;
	
	public ManufactureFitting() {
	}

	public ManufactureFitting(Integer id) {
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

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackBag() {
		return this.packBag;
	}

	public void setPackBag(String packBag) {
		this.packBag = packBag;
	}

	public String getPictPaths() {
		return this.pictPaths;
	}

	public void setPictPaths(String pictPaths) {
		this.pictPaths = pictPaths;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Manufacture getOrderManufacture() {
		return this.orderManufacture;
	}

	public void setOrderManufacture(Manufacture orderManufacture) {
		this.orderManufacture = orderManufacture;
	}

	public String getPictAttribute() {
		return pictAttribute;
	}

	public void setPictAttribute(String pictAttribute) {
		this.pictAttribute = pictAttribute;
	}

	public Integer getMftid() {
		return mftid;
	}

	public void setMftid(Integer mftid) {
		this.mftid = mftid;
	}
}