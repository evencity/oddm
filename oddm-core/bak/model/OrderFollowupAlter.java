package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the order_followup_alter database table.
 * 
 */
@Entity
@Table(name="order_followup_alter")
@NamedQuery(name="OrderFollowupAlter.findAll", query="SELECT o FROM OrderFollowupAlter o")
public class OrderFollowupAlter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@Temporal(TemporalType.DATE)
	@Column(name="date_factory")
	private Date dateFactory;

	@Temporal(TemporalType.DATE)
	@Column(name="date_shipment")
	private Date dateShipment;

	private String description;

	private String operator;

	@Column(name="shipment_no")
	private int shipmentNo;

	private String state;

	private Timestamp timestamp;

	//bi-directional many-to-one association to OrderFollowup
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_fllowupid")
	private OrderFollowup orderFollowup;

	public OrderFollowupAlter() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateFactory() {
		return this.dateFactory;
	}

	public void setDateFactory(Date dateFactory) {
		this.dateFactory = dateFactory;
	}

	public Date getDateShipment() {
		return this.dateShipment;
	}

	public void setDateShipment(Date dateShipment) {
		this.dateShipment = dateShipment;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getShipmentNo() {
		return this.shipmentNo;
	}

	public void setShipmentNo(int shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public OrderFollowup getOrderFollowup() {
		return this.orderFollowup;
	}

	public void setOrderFollowup(OrderFollowup orderFollowup) {
		this.orderFollowup = orderFollowup;
	}

}