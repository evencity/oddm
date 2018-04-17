package com.apical.oddm.facade.manufacture.dto;

import java.io.Serializable;

public class ManufactureFittingDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private String label;

	private String name;

	private String packBag;

	private String pictPaths;

	private String specification;

	private String updatetime;
	
	private String pictAttribute;
	
	public String getPictAttribute() {
		return pictAttribute;
	}

	public void setPictAttribute(String pictAttribute) {
		this.pictAttribute = pictAttribute;
	}

	public Integer getId() {
		return id;
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

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}
