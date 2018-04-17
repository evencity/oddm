package com.apical.oddm.facade.sale.lc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class SaleLcDto {
	private Integer id;

	private String clientName;

	private BigDecimal creditAmount;

	private String creditNo;

	private String currency;

	private Date dateDelivery;

	private Date dateGet;

	private String description;

	private BigDecimal invoiceAmount;

	private String invoiceNo;

	private String orderNo;

	private String spotForward;

	private Timestamp timestamp;

	private Timestamp updatetime;
	
	public SaleLcDto() {
	}

	public SaleLcDto(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public BigDecimal getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getCreditNo() {
		return this.creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDateDelivery() {
		return this.dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public Date getDateGet() {
		return this.dateGet;
	}

	public void setDateGet(Date dateGet) {
		this.dateGet = dateGet;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSpotForward() {
		return this.spotForward;
	}

	public void setSpotForward(String spotForward) {
		this.spotForward = spotForward;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}