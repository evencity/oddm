package com.apical.oddm.facade.sale.pi;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class SalePiCmd {

	private Integer id;

	private BigDecimal amount;

	private String seller;

	private String clientName;

	private String currency;

	private String description;

	private String district;

	private Date orderDate;

	private String orderMonth;

	private String piNo;

	private String poNo;

	private String summary;

	private Timestamp timestamp;

	private Timestamp updatetime;
	
	public SalePiCmd() {
	}

	public SalePiCmd(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getPiNo() {
		return this.piNo;
	}

	public void setPiNo(String piNo) {
		this.piNo = piNo;
	}

	public String getPoNo() {
		return this.poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	@Override
	public String toString() {
		return "SalePiCmd [id=" + id + ", amount=" + amount + ", seller="
				+ seller + ", clientName=" + clientName + ", currency="
				+ currency + ", description=" + description + ", district="
				+ district + ", orderDate=" + orderDate + ", piNo=" + piNo
				+ ", poNo=" + poNo + ", summary=" + summary + ", timestamp="
				+ timestamp + ", updatetime=" + updatetime + "]";
	}

	public String getOrderMonth() {
		return orderMonth;
	}

	public void setOrderMonth(String orderMonth) {
		this.orderMonth = orderMonth;
	}

}