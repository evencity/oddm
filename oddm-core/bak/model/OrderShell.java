package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the order_shell database table.
 * 
 */
@Entity
@Table(name="order_shell")
@NamedQuery(name="OrderShell.findAll", query="SELECT o FROM OrderShell o")
public class OrderShell implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private String color;

	private String craft;

	private String name;

	@Column(name="silk_screen")
	private byte silkScreen;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	public OrderShell() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getSilkScreen() {
		return this.silkScreen;
	}

	public void setSilkScreen(byte silkScreen) {
		this.silkScreen = silkScreen;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

}