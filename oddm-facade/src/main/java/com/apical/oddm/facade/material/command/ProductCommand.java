package com.apical.oddm.facade.material.command;

import java.io.Serializable;

/**
 * The persistent class for the product database table.
 * 
 */

public class ProductCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private String name;

	private Integer state;
	
	private String type;
	
	private String materialBareIds;
	
	private String materialFittingIds;
	
	private String materialPackingIds;
	
	private Integer typeId;

	public String getMaterialBareIds() {
		return materialBareIds;
	}

	
	public Integer getTypeId() {
		return typeId;
	}


	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}


	public void setMaterialBareIds(String materialBareIds) {
		this.materialBareIds = materialBareIds;
	}

	public String getMaterialFittingIds() {
		return materialFittingIds;
	}

	public void setMaterialFittingIds(String materialFittingIds) {
		this.materialFittingIds = materialFittingIds;
	}

	public String getMaterialPackingIds() {
		return materialPackingIds;
	}

	public void setMaterialPackingIds(String materialPackingIds) {
		this.materialPackingIds = materialPackingIds;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProductCommand [id=" + id + ", description=" + description + ", name=" + name + ", state=" + state + ", type=" + type + ", materialBareIds=" + materialBareIds
				+ ", materialFittingIds=" + materialFittingIds + ", materialPackingIds=" + materialPackingIds + "]";
	}
}