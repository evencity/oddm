package com.apical.oddm.core.model.encase;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * The persistent class for the invoice_list database table.
 * 
 */
@Entity
@Table(name="invoice_list")
@NamedQuery(name="InvoiceList.findAll", query="SELECT i FROM InvoiceList i")
public class InvoiceList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String model;

	@Column(name="order_no")
	private String orderNo;

	private Integer qty;

	@Column(name="unit_price")
	private BigDecimal unitPrice;

	//bi-directional many-to-one association to InvoiceInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="invoiceid")
	private InvoiceInfo invoiceInfo;

	public InvoiceList() {
	}

	public InvoiceList(Integer id) {
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

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public InvoiceInfo getInvoiceInfo() {
		return this.invoiceInfo;
	}

	public void setInvoiceInfo(InvoiceInfo invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

}