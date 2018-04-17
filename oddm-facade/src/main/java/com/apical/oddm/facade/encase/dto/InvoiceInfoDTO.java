package com.apical.oddm.facade.encase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InvoiceInfoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String address;

	private String brand;

	private String currency;

	private Date dateInvoice;
	private String dateInvoiceString;

	private String fax;

	private String incoterms;

	private String name;

	private String companyName;

	private String companyAddr;
	
	private String origion;

	private String payment;

	private String piNo;

	private String shippingMethod;

	private String tel;

	private String timestamp;
	
	private String updatetime;

	private String to_;
	
	private BigDecimal qtys; //总订单数量

	private BigDecimal amount;//总金额
	
	private String dateInvoiceStart;//供传查询用
	
	private String dateInvoiceEnd;//供传查询用

	private List<InvoiceListDTO> invoiceListDTOs;
	
	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
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

	public String getDateInvoiceStart() {
		return dateInvoiceStart;
	}

	public void setDateInvoiceStart(String dateInvoiceStart) {
		this.dateInvoiceStart = dateInvoiceStart;
	}

	public String getDateInvoiceEnd() {
		return dateInvoiceEnd;
	}

	public void setDateInvoiceEnd(String dateInvoiceEnd) {
		this.dateInvoiceEnd = dateInvoiceEnd;
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
		return dateInvoice;
	}

	public void setDateInvoice(Date dateInvoice) {
		this.dateInvoice = dateInvoice;
	}

	public String getDateInvoiceString() {
		return dateInvoiceString;
	}

	public void setDateInvoiceString(String dateInvoiceString) {
		this.dateInvoiceString = dateInvoiceString;
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

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTo_() {
		return to_;
	}

	public void setTo_(String to_) {
		this.to_ = to_;
	}

	public List<InvoiceListDTO> getInvoiceListDTOs() {
		return invoiceListDTOs;
	}

	public void setInvoiceListDTOs(List<InvoiceListDTO> invoiceListDTOs) {
		this.invoiceListDTOs = invoiceListDTOs;
	}


	

}
