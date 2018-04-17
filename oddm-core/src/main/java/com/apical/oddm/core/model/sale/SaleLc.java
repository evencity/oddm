package com.apical.oddm.core.model.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the sale_lc database table.
 * 
 */
@Entity
@Table(name="sale_lc")
@NamedQuery(name="SaleLc.findAll", query="SELECT s FROM SaleLc s")
public class SaleLc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="client_name")
	private String clientName;

	@Column(name="credit_amount")
	private BigDecimal creditAmount;

	@Column(name="credit_no")
	private String creditNo;

	@Column()
	private String currency;

	@Temporal(TemporalType.DATE)
	@Column(name="date_delivery")
	private Date dateDelivery;

	@Temporal(TemporalType.DATE)
	@Column(name="date_get")
	private Date dateGet;

	@Column()
	private String description;

	@Column(name="invoice_amount")
	private BigDecimal invoiceAmount;

	@Column(name="invoice_no")
	private String invoiceNo;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="spot_forward")
	private String spotForward;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date dateDeliveryStart;

	@Transient
	@Temporal(TemporalType.DATE)
	private Date dateDeliveryEnd;
	
	public SaleLc() {
	}

	public SaleLc(Integer id) {
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

	public Date getDateDeliveryStart() {
		return dateDeliveryStart;
	}

	public void setDateDeliveryStart(Date dateDeliveryStart) {
		this.dateDeliveryStart = dateDeliveryStart;
	}

	public Date getDateDeliveryEnd() {
		return dateDeliveryEnd;
	}

	public void setDateDeliveryEnd(Date dateDeliveryEnd) {
		this.dateDeliveryEnd = dateDeliveryEnd;
	}

}