package com.apical.oddm.core.model.produce;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the order_record_type database table.
 * 
 */
@Entity
@Table(name="order_record_type")
@NamedQuery(name="OrderRecordType.findAll", query="SELECT o FROM OrderRecordType o")
public class OrderRecordType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	//bi-directional many-to-one association to OrderRecord
	@OneToMany(mappedBy="orderRecordType")
	private Set<OrderRecord> orderRecords;

	public OrderRecordType() {
	}

	public OrderRecordType(Integer id) {
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

	public Set<OrderRecord> getOrderRecords() {
		return this.orderRecords;
	}

	public void setOrderRecords(Set<OrderRecord> orderRecords) {
		this.orderRecords = orderRecords;
	}

	public OrderRecord addOrderRecord(OrderRecord orderRecord) {
		getOrderRecords().add(orderRecord);
		orderRecord.setOrderRecordType(this);

		return orderRecord;
	}

	public OrderRecord removeOrderRecord(OrderRecord orderRecord) {
		getOrderRecords().remove(orderRecord);
		orderRecord.setOrderRecordType(null);

		return orderRecord;
	}

}