package com.apical.oddm.facade.order.command;

import java.io.Serializable;

import org.springframework.context.annotation.Description;


/**
 * The persistent class for the order_packing database table.
 * 
 */
public class OrderPackingCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;//物料名称

	private String specification;//规格型号

	private String supplier;//供应商

	private String orderId;
	
	private Integer isNew;
	
	@Description(value="新旧物料")
	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Description(value="规格型号")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Description(value="供应商")
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderPacking [id=" + id + ", name=" + name + ", specification=" + specification + ", supplier=" + supplier + ", orderId=" + orderId + "]";
	}
}