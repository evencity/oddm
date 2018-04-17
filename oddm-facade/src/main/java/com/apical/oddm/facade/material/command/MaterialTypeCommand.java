package com.apical.oddm.facade.material.command;

import java.io.Serializable;



/**
 * The persistent class for the material_type database table.
 * 
 */
public class MaterialTypeCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer type;  //1-裸机物料；2-包材物料；3-配件物料；

	private String name;

	private String description;

	public Integer getId() {
		return id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "MaterialTypeCommand [id=" + id + ", type=" + type + ", name=" + name + ", description=" + description + "]";
	}

}