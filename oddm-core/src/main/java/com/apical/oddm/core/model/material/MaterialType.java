package com.apical.oddm.core.model.material;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * The persistent class for the material_type database table.
 * 
 */
@Entity
@Table(name="material_type")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MaterialType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer type;

	private String name;

	private String description;

	//bi-directional many-to-one association to MaterialBare
	@OneToMany(mappedBy="materialType")
	private Set<MaterialBare> materialBares = new HashSet<MaterialBare>(0);

	//bi-directional many-to-one association to MaterialPacking
	@OneToMany(mappedBy="materialType")
	private Set<MaterialPacking> materialPackings = new HashSet<MaterialPacking>(0);

	public MaterialType() {
	}

	public MaterialType(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MaterialBare> getMaterialBares() {
		return this.materialBares;
	}

	public void setMaterialBares(Set<MaterialBare> materialBares) {
		this.materialBares = materialBares;
	}

	public MaterialBare addMaterialBare(MaterialBare materialBare) {
		getMaterialBares().add(materialBare);
		materialBare.setMaterialType(this);

		return materialBare;
	}

	public MaterialBare removeMaterialBare(MaterialBare materialBare) {
		getMaterialBares().remove(materialBare);
		materialBare.setMaterialType(null);

		return materialBare;
	}

	public Set<MaterialPacking> getMaterialPackings() {
		return this.materialPackings;
	}

	public void setMaterialPackings(Set<MaterialPacking> materialPackings) {
		this.materialPackings = materialPackings;
	}

	public MaterialPacking addMaterialPacking(MaterialPacking materialPacking) {
		getMaterialPackings().add(materialPacking);
		materialPacking.setMaterialType(this);
		return materialPacking;
	}

	public MaterialPacking removeMaterialPacking(MaterialPacking materialPacking) {
		getMaterialPackings().remove(materialPacking);
		materialPacking.setMaterialType(null);
		return materialPacking;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}