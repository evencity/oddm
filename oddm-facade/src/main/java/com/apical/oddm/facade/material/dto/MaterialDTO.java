package com.apical.oddm.facade.material.dto;

import java.io.Serializable;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2017年2月7日 上午9:01:07 
 * @version 1.0 
 */

public class MaterialDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private String name;

	private Integer type;

	private Integer isbase;
	
	private Integer tb;

	private MaterialTypeDTO materialTypeDTO;
	
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public MaterialTypeDTO getMaterialTypeDTO() {
		return materialTypeDTO;
	}

	public void setMaterialTypeDTO(MaterialTypeDTO materialTypeDTO) {
		this.materialTypeDTO = materialTypeDTO;
	}

}
