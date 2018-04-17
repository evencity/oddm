package com.apical.oddm.facade.sale.proto;

import java.sql.Timestamp;
import java.util.Date;

public class SaleProtoCmd {
	private Integer id;

	private String clientName;

	private Date datePayed;

	private Date dateSend;

	private String description;

	private String district;

	private String facade;

	private String payed;

	private String pcba;

	private String productName;

	private Integer quantity;

	private Date dateReturn;
	
	private String shell;

	private Float size;

	private Timestamp timestamp;

	private Timestamp updatetime;

	private String dateSendStart;

	private String dateSendEnd;
	
	public SaleProtoCmd() {
	}

	public SaleProtoCmd(Integer id) {
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public String getDateSendEnd() {
		return dateSendEnd;
	}

	public void setDateSendEnd(String dateSendEnd) {
		this.dateSendEnd = dateSendEnd;
	}

	public String getDateSendStart() {
		return dateSendStart;
	}

	public void setDateSendStart(String dateSendStart) {
		this.dateSendStart = dateSendStart;
	}

	public Date getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

}