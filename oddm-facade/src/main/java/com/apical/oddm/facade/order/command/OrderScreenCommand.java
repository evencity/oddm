package com.apical.oddm.facade.order.command;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the order_hardware database table.
 * 
 */
public class OrderScreenCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;//物料名称

	private String specification;//规格型号

	private String supplier;//供应商

	private String orderId;

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

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

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
		return "OrderHardware [id=" + id + ", name=" + name + ", specification=" + specification + ", supplier=" + supplier + ", orderId=" + orderId + "]";
	}
	
}