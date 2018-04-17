package com.apical.oddm.facade.bom.dto;

import java.io.Serializable;
/**
 * 2.4.4 BOM物料
 * @author 
 *
 */
public class BomMaterialDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String mtlCode;

	private String description;

	private String materialName;

	private String specification;
	
	private String sort;
	
	private String updatetime; //可以用Date类型，但是得加上@Temporal(TemporalType.TIMESTAMP)
	
	public String getMtlCode() {
		return this.mtlCode;
	}

	public void setMtlCode(String mtlCode) {
		this.mtlCode = mtlCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}


	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}
