package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;


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
	private int id;

	private String description;

	private String model;

	@Column(name="order_no")
	private String orderNo;

	private int qty;

	@Column(name="unit_price")
	private int unitPrice;

	//bi-directional many-to-one association to InvoiceInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="invoiceid")
	private InvoiceInfo invoiceInfo;

	public InvoiceList() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public int getQty() {
		return this.qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public InvoiceInfo getInvoiceInfo() {
		return this.invoiceInfo;
	}

	public void setInvoiceInfo(InvoiceInfo invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

}