package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the invoice_info database table.
 * 
 */
@Entity
@Table(name="invoice_info")
@NamedQuery(name="InvoiceInfo.findAll", query="SELECT i FROM InvoiceInfo i")
public class InvoiceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String address;

	private String brand;

	private String currency;

	@Temporal(TemporalType.DATE)
	@Column(name="date_invoice")
	private Date dateInvoice;

	private String fax;

	private String incoterms;

	private String name;

	private String origion;

	private String payment;

	@Column(name="pi_no")
	private String piNo;

	@Column(name="shipping_method")
	private String shippingMethod;

	private String tel;

	private Timestamp timestamp;

	@Column(name="to_")
	private String to;

	//bi-directional many-to-one association to InvoiceList
	@OneToMany(mappedBy="invoiceInfo")
	private Set<InvoiceList> invoiceLists;

	public InvoiceInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDateInvoice() {
		return this.dateInvoice;
	}

	public void setDateInvoice(Date dateInvoice) {
		this.dateInvoice = dateInvoice;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIncoterms() {
		return this.incoterms;
	}

	public void setIncoterms(String incoterms) {
		this.incoterms = incoterms;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrigion() {
		return this.origion;
	}

	public void setOrigion(String origion) {
		this.origion = origion;
	}

	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPiNo() {
		return this.piNo;
	}

	public void setPiNo(String piNo) {
		this.piNo = piNo;
	}

	public String getShippingMethod() {
		return this.shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Set<InvoiceList> getInvoiceLists() {
		return this.invoiceLists;
	}

	public void setInvoiceLists(Set<InvoiceList> invoiceLists) {
		this.invoiceLists = invoiceLists;
	}

	public InvoiceList addInvoiceList(InvoiceList invoiceList) {
		getInvoiceLists().add(invoiceList);
		invoiceList.setInvoiceInfo(this);

		return invoiceList;
	}

	public InvoiceList removeInvoiceList(InvoiceList invoiceList) {
		getInvoiceLists().remove(invoiceList);
		invoiceList.setInvoiceInfo(null);

		return invoiceList;
	}

}