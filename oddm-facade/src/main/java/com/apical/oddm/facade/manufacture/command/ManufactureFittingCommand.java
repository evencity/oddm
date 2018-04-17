package com.apical.oddm.facade.manufacture.command;

import java.io.Serializable;

import org.springframework.context.annotation.Description;

public class ManufactureFittingCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer state;

	private String description;

	private String label;

	private String name;

	private String orderNo;

	private String packBag;

	private String pictPaths;

	private String specification;

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

	@Description(value="备注")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(value="标贴")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Description(value="名称")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Description(value="包装袋")
	public String getPackBag() {
		return packBag;
	}

	public void setPackBag(String packBag) {
		this.packBag = packBag;
	}

	@Description(value="图片")
	public String getPictPaths() {
		return pictPaths;
	}

	public void setPictPaths(String pictPaths) {
		this.pictPaths = pictPaths;
	}

	@Description(value="型号/规格")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		return "ManufactureFittingCommand [id=" + id + ", description=" + description + ", label=" + label + ", name=" + name + ", packBag=" + packBag + ", pictPaths=" + pictPaths
				+ ", specification=" + specification + ", updatetime=" + updatetime + "]";
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
