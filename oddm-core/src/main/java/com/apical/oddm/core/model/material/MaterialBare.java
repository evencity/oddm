package com.apical.oddm.core.model.material;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * The persistent class for the material_bare database table.
 * 
 */
@Entity
@Table(name="material_bare")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MaterialBare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	@Column(name="type", updatable=false, insertable=false)
	private Integer type;

	private Integer isbase;

	//bi-directional many-to-one association to MaterialType
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type")
	private MaterialType materialType;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "product_bare", joinColumns = { @JoinColumn(name = "product_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "bare_id", nullable = false, updatable = false) })
	@OrderBy("id ASC")
	private Set<Product> products = new HashSet<Product>(0);

	public MaterialBare(Integer id) {
		this.id = id;
	}

	public MaterialBare() {
	}
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MaterialType getMaterialType() {
		return this.materialType;
	}

	public void setMaterialType(MaterialType materialType) {
		this.materialType = materialType;
	}

	public Integer getIsbase() {
		return isbase;
	}

	public void setIsbase(Integer isbase) {
		this.isbase = isbase;
	}

}