package com.apical.oddm.facade.manufacture.command;

import java.io.Serializable;

import org.springframework.context.annotation.Description;

public class ManufactureShellCommand implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer state;

	private String color;

	private String craft;

	private String description;

	private String name;

	private String orderNo;

	private String pictPaths;

	private String silk;

	private String updatetime;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Description(value="颜色")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Description(value="喷涂工艺")
	public String getCraft() {
		return craft;
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}

	@Description(value="备注")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(value="名称")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Description(value="图片")
	public String getPictPaths() {
		return pictPaths;
	}

	public void setPictPaths(String pictPaths) {
		this.pictPaths = pictPaths;
	}

	@Description(value="丝印")
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

	@Override
	public String toString() {
		return "ManufactureShellCommand [id=" + id + ", color=" + color + ", craft=" + craft + ", description=" + description + ", name=" + name + ", pictPaths=" + pictPaths
				+ ", silk=" + silk + ", updatetime=" + updatetime + "]";
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
