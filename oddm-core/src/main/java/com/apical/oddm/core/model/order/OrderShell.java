package com.apical.oddm.core.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


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
	private Integer id;

	private String color;

	private String craft;

	private String name;

	@Column(name="isnew")
	private Integer isNew;

	@Column(name="silk_screen")
	private Integer silkScreen;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	public OrderShell() {
	}

	public OrderShell(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsNew() {
		return isNew;
	}
	
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
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

	public Integer getSilkScreen() {
		return this.silkScreen;
	}

	public void setSilkScreen(Integer silkScreen) {
		this.silkScreen = silkScreen;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

}