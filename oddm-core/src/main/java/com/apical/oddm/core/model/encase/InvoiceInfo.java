package com.apical.oddm.core.model.encase;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
	private Integer id;

	private String address;

	private String brand;

	private String currency;

	@Temporal(TemporalType.DATE)
	@Column(name="date_invoice")
	private Date dateInvoice;

	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateInvoiceStart;//供传查询用
	
	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateInvoiceEnd;//供传查询用
	
	private String fax;

	private String incoterms;

	private String name;

	@Column(name="company_name")
	private String companyName;

	@Column(name="company_addr")
	private String companyAddr;
	
	private String origion;

	private String payment;

	@Column(name="pi_no")
	private String piNo;

	@Column(name="shipping_method")
	private String shippingMethod;

	private String tel;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;
	
	private String to_;

	@Transient
	private BigDecimal qtys; //总订单数量

	@Transient
	private BigDecimal amount;//总金额
	
	//bi-directional many-to-one association to InvoiceList
	@SuppressWarnings("deprecation")
	@OneToMany(mappedBy="invoiceInfo")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy()
	private Set<InvoiceList> invoiceLists;

	public InvoiceInfo() {
	}

	public InvoiceInfo(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
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

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getTo_() {
		return to_;
	}

	public void setTo_(String to_) {
		this.to_ = to_;
	}

	public BigDecimal getQtys() {
		return qtys;
	}

	public void setQtys(BigDecimal qtys) {
		this.qtys = qtys;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDateInvoiceStart() {
		return dateInvoiceStart;
	}

	public void setDateInvoiceStart(Date dateInvoiceStart) {
		this.dateInvoiceStart = dateInvoiceStart;
	}

	public Date getDateInvoiceEnd() {
		return dateInvoiceEnd;
	}

	public void setDateInvoiceEnd(Date dateInvoiceEnd) {
		this.dateInvoiceEnd = dateInvoiceEnd;
	}
	
}