package com.apical.oddm.facade.material.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The persistent class for the product database table.
 * 
 */

public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private String name;
	
	private Integer typeId;
	
	private String typeName;

	private Integer state;
	
	private Set<MaterialBareDTO> materialBareDTOs = new HashSet<MaterialBareDTO>(0);
	
	private Set<MaterialPackingDTO>  materialPackingDTOs= new HashSet<MaterialPackingDTO>(0);

	private Set<MaterialFittingDTO> materialFittingDTOs = new HashSet<MaterialFittingDTO>(0);

	public Set<MaterialBareDTO> getMaterialBareDTOs() {
		return materialBareDTOs;
	}

	public void setMaterialBareDTOs(Set<MaterialBareDTO> materialBareDTOs) {
		this.materialBareDTOs = materialBareDTOs;
	}

	public Set<MaterialPackingDTO> getMaterialPackingDTOs() {
		return materialPackingDTOs;
	}
	public void setMaterialPackingDTOs(Set<MaterialPackingDTO> materialPackingDTOs) {
		this.materialPackingDTOs = materialPackingDTOs;
	}

	public Set<MaterialFittingDTO> getMaterialFittingDTOs() {
		return materialFittingDTOs;
	}

	public void setMaterialFittingDTOs(Set<MaterialFittingDTO> materialFittingDTOs) {
		this.materialFittingDTOs = materialFittingDTOs;
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


	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	@Override
	public String toString() {
		final int maxLen = 10;
		return "ProductDTO [id=" + id + ", description=" + description + ", name=" + name + ", typeId=" + typeId + ", typeName=" + typeName + ", state=" + state
				+ ", materialBareDTOs=" + (materialBareDTOs != null ? toString(materialBareDTOs, maxLen) : null) + ", materialPackingDTOs="
				+ (materialPackingDTOs != null ? toString(materialPackingDTOs, maxLen) : null) + ", materialFittingDTOs="
				+ (materialFittingDTOs != null ? toString(materialFittingDTOs, maxLen) : null) + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


}