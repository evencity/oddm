package com.apical.oddm.facade.material.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the material_bare database table.
 * 
 */
public class MaterialFittingDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private String name;

	private String typeName;
	
	private Integer typeId;
	
	private Integer isbase;
	
	public Integer getIsbase() {
		return isbase;
	}

	public void setIsbase(Integer isbase) {
		this.isbase = isbase;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Override
	public String toString() {
		return "MaterialFittingDTO [id=" + id + ", description=" + description + ", name=" + name + ", typeName=" + typeName + ", typeId=" + typeId + "]";
	}

}