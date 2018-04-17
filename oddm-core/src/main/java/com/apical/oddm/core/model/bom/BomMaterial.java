package com.apical.oddm.core.model.bom;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the bom_material database table.
 * 
 */
@Entity
@Table(name="bom_material")
@NamedQuery(name="BomMaterial.findAll", query="SELECT b FROM BomMaterial b")
public class BomMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="mtl_code")
	private String mtlCode;

	private String description;

	@NotNull
	@Column(name="material_name")
	private String materialName;

	private String specification;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false)  //updatetime 设置程序不自动更新  。 数据库设置自动更新`updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	private Timestamp updatetime; //可以用Date类型，但是得加上@Temporal(TemporalType.TIMESTAMP)

	public BomMaterial() {
	}
	
	public BomMaterial(String mtlCode, String materialName, String specification, String description) {
		this.mtlCode = mtlCode;
		this.description = description;
		this.materialName = materialName;
		this.specification = specification;
	}


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

	public Timestamp getUpdatetime() {
		return updatetime;
	}

}