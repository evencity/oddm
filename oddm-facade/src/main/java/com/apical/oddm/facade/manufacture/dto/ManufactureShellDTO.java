package com.apical.oddm.facade.manufacture.dto;

import java.io.Serializable;

public class ManufactureShellDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String color;

	private String craft;

	private String description;

	private String name;

	private String pictPaths;

	private String silk;

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

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCraft() {
		return this.craft;
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPictPaths() {
		return this.pictPaths;
	}

	public void setPictPaths(String pictPaths) {
		this.pictPaths = pictPaths;
	}

	public String getSilk() {
		return silk;
	}

	public void setSilk(String silk) {
		this.silk = silk;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}
