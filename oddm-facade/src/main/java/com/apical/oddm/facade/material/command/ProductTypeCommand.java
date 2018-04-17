package com.apical.oddm.facade.material.command;

import java.io.Serializable;
/**
 * The persistent class for the product_type database table.
 * 
 */
public class ProductTypeCommand implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;

	private String description;

	private String name;
	
	/*private Set<ProductCommand> products = new HashSet<ProductCommand>(0);

	private Set<ProductCheck> ProductChecks = new HashSet<ProductCheck>(0);*/
	

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

	@Override
	public String toString() {
		return "ProductTypeCommand [id=" + id + ", description=" + description + ", name=" + name + "]";
	}

}