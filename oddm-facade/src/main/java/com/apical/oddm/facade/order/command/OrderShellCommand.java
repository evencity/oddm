package com.apical.oddm.facade.order.command;

import java.io.Serializable;

import org.springframework.context.annotation.Description;


/**
 * The persistent class for the order_shell database table.
 * 
 */
public class OrderShellCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String color;//颜色

	private String craft;//工艺

	private String name;//外壳名称

	private Integer silkScreen;//是否有银丝印

	private Integer isNew;
	
	@Description(value="新旧物料")
	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	private String orderId;

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

	@Description(value="工艺")
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

	@Description(value="丝印")
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