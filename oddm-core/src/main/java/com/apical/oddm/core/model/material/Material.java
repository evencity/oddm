package com.apical.oddm.core.model.material;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="material")
public class Material implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String description;

	private String name;

	@Column(name="type", updatable=false, insertable=false)
	private Integer type;

	private Integer isbase;
	
	private Integer tb;

	//bi-directional many-to-one association to MaterialType
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type")
	private MaterialType materialType;

	public Material(Integer id) {
		this.id = id;
	}

	public Material() {
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

	public Integer getTb() {
		return tb;
	}

	public void setTb(Integer tb) {
		this.tb = tb;
	}

}