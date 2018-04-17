package com.apical.oddm.core.model.produce;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.apical.oddm.core.model.order.OrderInfo;

import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the order_prototype database table.
 * 
 */
@Entity
@Table(name="order_prototype")
@NamedQuery(name="OrderPrototype.findAll", query="SELECT o FROM OrderPrototype o")
public class OrderPrototype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer state;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	//bi-directional many-to-one association to OrderPrototypeDetial
	@SuppressWarnings("deprecation")
	@OneToMany(mappedBy="orderPrototype")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy()
	private Set<OrderPrototypeDetial> orderPrototypeDetials;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orderid")
	private OrderInfo orderInfo;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="orderid", updatable=false, insertable=false)
	private Integer orderId;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public OrderPrototype() {
	}

	public OrderPrototype(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Set<OrderPrototypeDetial> getOrderPrototypeDetials() {
		return this.orderPrototypeDetials;
	}

	public void setOrderPrototypeDetials(Set<OrderPrototypeDetial> orderPrototypeDetials) {
		this.orderPrototypeDetials = orderPrototypeDetials;
	}

	public OrderPrototypeDetial addOrderPrototypeDetial(OrderPrototypeDetial orderPrototypeDetial) {
		getOrderPrototypeDetials().add(orderPrototypeDetial);
		orderPrototypeDetial.setOrderPrototype(this);

		return orderPrototypeDetial;
	}

	public OrderPrototypeDetial removeOrderPrototypeDetial(OrderPrototypeDetial orderPrototypeDetial) {
		getOrderPrototypeDetials().remove(orderPrototypeDetial);
		orderPrototypeDetial.setOrderPrototype(null);

		return orderPrototypeDetial;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

}