package com.apical.oddm.core.model.produce;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the order_mft_package_title database table.
 * 
 */
@Entity
@Table(name="order_mft_package_title")
@NamedQuery(name="ManufacturePackageTitle.findAll", query="SELECT o FROM ManufacturePackageTitle o")
public class ManufacturePackageTitle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private Integer seq;

	private String version;
	
	private String description;
	
	//bi-directional many-to-one association to OrderMftPackage
	@OneToMany(mappedBy="orderMftPackageTitle")
	private Set<ManufacturePackage> orderMftPackages;

	public ManufacturePackageTitle() {
	}

	public ManufacturePackageTitle(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Set<ManufacturePackage> getOrderMftPackages() {
		return this.orderMftPackages;
	}

	public void setOrderMftPackages(Set<ManufacturePackage> orderMftPackages) {
		this.orderMftPackages = orderMftPackages;
	}

	public ManufacturePackage addOrderMftPackage(ManufacturePackage orderMftPackage) {
		getOrderMftPackages().add(orderMftPackage);
		orderMftPackage.setOrderMftPackageTitle(this);

		return orderMftPackage;
	}

	public ManufacturePackage removeOrderMftPackage(ManufacturePackage orderMftPackage) {
		getOrderMftPackages().remove(orderMftPackage);
		orderMftPackage.setOrderMftPackageTitle(null);

		return orderMftPackage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}