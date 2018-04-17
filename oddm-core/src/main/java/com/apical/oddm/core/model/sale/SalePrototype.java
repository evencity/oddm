package com.apical.oddm.core.model.sale;

import java.io.Serializable;
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
 * The persistent class for the sale_prototype database table.
 * 
 */
@Entity
@Table(name="sale_prototype")
@NamedQuery(name="SalePrototype.findAll", query="SELECT s FROM SalePrototype s")
public class SalePrototype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="client_name")
	private String clientName;

	@Temporal(TemporalType.DATE)
	@Column(name="date_payed")
	private Date datePayed;

	@Temporal(TemporalType.DATE)
	@Column(name="date_send")
	private Date dateSend;

	private String description;

	private String district;

	private String facade;

	private String payed;

	private String pcba;

	@Column(name="product_name")
	private String productName;

	private Integer quantity;

	@Temporal(TemporalType.DATE)
	@Column(name="date_return")
	private Date dateReturn;

	private String shell;

	private Float size;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	@Transient
	@Temporal(TemporalType.DATE)
	private Date dateSendStart;

	@Transient
	@Temporal(TemporalType.DATE)
	private Date dateSendEnd;

	public SalePrototype() {
	}

	public SalePrototype(Integer id) {
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

	public Date getDatePayed() {
		return this.datePayed;
	}

	public void setDatePayed(Date datePayed) {
		this.datePayed = datePayed;
	}

	public Date getDateSend() {
		return this.dateSend;
	}

	public void setDateSend(Date dateSend) {
		this.dateSend = dateSend;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getFacade() {
		return this.facade;
	}

	public void setFacade(String facade) {
		this.facade = facade;
	}

	public String getPayed() {
		return this.payed;
	}

	public void setPayed(String payed) {
		this.payed = payed;
	}

	public String getPcba() {
		return this.pcba;
	}

	public void setPcba(String pcba) {
		this.pcba = pcba;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

	public String getShell() {
		return this.shell;
	}

	public void setShell(String shell) {
		this.shell = shell;
	}

	public Float getSize() {
		return this.size;
	}

	public void setSize(Float size) {
		this.size = size;
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

	public Date getDateSendStart() {
		return dateSendStart;
	}

	public void setDateSendStart(Date dateSendStart) {
		this.dateSendStart = dateSendStart;
	}

	public Date getDateSendEnd() {
		return dateSendEnd;
	}

	public void setDateSendEnd(Date dateSendEnd) {
		this.dateSendEnd = dateSendEnd;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}