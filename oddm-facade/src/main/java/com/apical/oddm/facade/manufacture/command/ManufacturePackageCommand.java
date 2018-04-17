package com.apical.oddm.facade.manufacture.command;

import java.io.Serializable;

import org.springframework.context.annotation.Description;

public class ManufacturePackageCommand implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer state;

	private Integer titleId;

	private String titleName;
	
	private String orderNo;

	private String description;

	private String pictPaths;

	private String pictTitle;
	
	private String type;

	private Integer manufactureId;
	
	private String pictAttribute;
	
	public String getPictAttribute() {
		return pictAttribute;
	}

	public void setPictAttribute(String pictAttribute) {
		this.pictAttribute = pictAttribute;
	}

	public Integer getManufactureId() {
		return manufactureId;
	}

	public void setManufactureId(Integer manufactureId) {
		this.manufactureId = manufactureId;
	}

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Description(value="描述")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(value="图片")
	public String getPictPaths() {
		return this.pictPaths;
	}

	public void setPictPaths(String pictPaths) {
		this.pictPaths = pictPaths;
	}

	@Description(value="标题")
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

	@Override
	public String toString() {
		return "ManufacturePackageCommand [id=" + id + ", titleId=" + titleId + ", description=" + description + ", pictPaths=" + pictPaths + ", pictTitle=" + pictTitle
				+ ", type=" + type + "]";
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
