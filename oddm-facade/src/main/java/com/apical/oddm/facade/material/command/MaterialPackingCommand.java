package com.apical.oddm.facade.material.command;

import java.io.Serializable;


/**
 * The persistent class for the material_packing database table.
 * 
 */
public class MaterialPackingCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private String name;

	private Integer typeId; 
	
	private String typeName; 
	
	private Integer normal; //1-通用；2-客制化
	
	/*private MaterialTypeCommand materialType;
	private Set<ProductCommand> products = new HashSet<ProductCommand>(0);
*/
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

	public Integer getNormal() {
		return normal;
	}

	public void setNormal(Integer normal) {
		this.normal = normal;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "MaterialPackingCommand [id=" + id + ", description=" + description + ", name=" + name + ", typeId=" + typeId + ", typeName=" + typeName + ", normal=" + normal
				+ "]";
	}

	
}