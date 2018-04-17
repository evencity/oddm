package com.apical.oddm.facade.manufacture.dto;

import java.io.Serializable;

public class ManufacturePackageDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private String pictPaths;

	private String pictTitle;
	
	private String type;
	
	private Integer titleId;

	private String pictAttribute;
	
	private ManufacturePackageTitleDTO manufacturePackageTitleDTO;
	
	
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
	
	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ManufacturePackageTitleDTO getManufacturePackageTitleDTO() {
		return manufacturePackageTitleDTO;
	}

	public void setManufacturePackageTitleDTO(ManufacturePackageTitleDTO manufacturePackageTitleDTO) {
		this.manufacturePackageTitleDTO = manufacturePackageTitleDTO;
	}

	
}
