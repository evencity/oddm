package com.apical.oddm.core.model.produce;

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
 * The persistent class for the order_prototype_detial database table.
 * 
 */
@Entity
@Table(name="order_prototype_detial")
@NamedQuery(name="OrderPrototypeDetial.findAll", query="SELECT o FROM OrderPrototypeDetial o")
public class OrderPrototypeDetial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	@Column(name="pict_paths")
	private String pictPaths;

	private Integer type;

	//bi-directional many-to-one association to OrderPrototype
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="proid")
	private OrderPrototype orderPrototype;

	public OrderPrototypeDetial() {
	}

	public OrderPrototypeDetial(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPictPaths() {
		return this.pictPaths;
	}

	public void setPictPaths(String pictPaths) {
		this.pictPaths = pictPaths;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public OrderPrototype getOrderPrototype() {
		return this.orderPrototype;
	}

	public void setOrderPrototype(OrderPrototype orderPrototype) {
		this.orderPrototype = orderPrototype;
	}

}