package com.apical.oddm.facade.order.dto;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the order_shell database table.
 * 
 */
public class OrderShellDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String color;

	private String craft;

	private String name;

	private Integer silkScreen;

	private String orderId;

	private Integer isNew;
	
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCraft() {
		return craft;
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSilkScreen() {
		return silkScreen;
	}

	public void setSilkScreen(Integer silkScreen) {
		this.silkScreen = silkScreen;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderShell [id=" + id + ", color=" + color + ", craft=" + craft + ", name=" + name + ", silkScreen=" + silkScreen + ", orderId=" + orderId + "]";
	}
}