package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the order_mft_check database table.
 * 
 */
@Entity
@Table(name="order_mft_check")
@NamedQuery(name="OrderMftCheck.findAll", query="SELECT o FROM OrderMftCheck o")
public class OrderMftCheck implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private byte ischeck;

	private String item;

	private String method;

	//bi-directional many-to-one association to OrderManufacture
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mft_id")
	private OrderManufacture orderManufacture;

	public OrderMftCheck() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(byte ischeck) {
		this.ischeck = ischeck;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public OrderManufacture getOrderManufacture() {
		return this.orderManufacture;
	}

	public void setOrderManufacture(OrderManufacture orderManufacture) {
		this.orderManufacture = orderManufacture;
	}

}