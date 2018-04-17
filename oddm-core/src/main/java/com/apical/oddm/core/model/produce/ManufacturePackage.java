package com.apical.oddm.core.model.produce;

import java.io.Serializable;

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
 * The persistent class for the order_mft_package database table.
 * 
 */
@Entity
@Table(name="order_mft_package")
@NamedQuery(name="ManufacturePackage.findAll", query="SELECT o FROM ManufacturePackage o")
public class ManufacturePackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	@Column(name="pict_paths")
	private String pictPaths;

	@Column(name="pict_title")
	private String pictTitle;

	@Column(name="pict_attribute")
	private String pictAttribute;
	
	//bi-directional many-to-one association to OrderMftPackageTitle
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="title_type")
	private ManufacturePackageTitle orderMftPackageTitle;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="mft_id", updatable=false, insertable=false)
	private Integer mftid;
	
	/**
	 * 只供获取，不能set
	 */
	@Column(name="title_type", updatable=false, insertable=false)
	private Integer titleId;
	
	//bi-directional many-to-one association to OrderManufacture
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mft_id")
	private Manufacture orderManufacture;

	public ManufacturePackage() {
	}

	public ManufacturePackage(Integer id) {
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

	public String getPictPaths() {
		return this.pictPaths;
	}

	public void setPictPaths(String pictPaths) {
		this.pictPaths = pictPaths;
	}

	public String getPictTitle() {
		return this.pictTitle;
	}

	public void setPictTitle(String pictTitle) {
		this.pictTitle = pictTitle;
	}

	public ManufacturePackageTitle getOrderMftPackageTitle() {
		return this.orderMftPackageTitle;
	}

	public void setOrderMftPackageTitle(ManufacturePackageTitle orderMftPackageTitle) {
		this.orderMftPackageTitle = orderMftPackageTitle;
	}

	public Manufacture getOrderManufacture() {
		return orderManufacture;
	}

	public void setOrderManufacture(Manufacture orderManufacture) {
		this.orderManufacture = orderManufacture;
	}

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
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