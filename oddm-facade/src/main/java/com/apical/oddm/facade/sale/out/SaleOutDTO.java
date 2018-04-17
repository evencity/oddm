package com.apical.oddm.facade.sale.out;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.apical.oddm.facade.order.dto.OrderInfoDTO;


public class SaleOutDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private OrderInfoDTO orderInfoDTO;

	private Integer orderId;
	
	private BigDecimal amount;

	private String currency;

	private Date dateCi;

	private Date dateInspection;

	private Date datePl;

	private Date dateShipment;

	private String freightForwarding;

	private String group;

	private String payed;

	private String scheduleSo;

	private Integer shipmentNo;

	private String status;

	private Timestamp timestamp;

	private Timestamp updatetime;

	private String productType;
	
	private Date dateShipmentStart;//下单时间，供传查询用
	private Date dateShipmentEnd;//下单时间，供传查询用
	private String dateShipmentMonth;//下单时间，供传查询用

	public SaleOutDTO() {
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

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDateCi() {
		return this.dateCi;
	}

	public void setDateCi(Date dateCi) {
		this.dateCi = dateCi;
	}

	public Date getDateInspection() {
		return this.dateInspection;
	}

	public void setDateInspection(Date dateInspection) {
		this.dateInspection = dateInspection;
	}

	public Date getDatePl() {
		return this.datePl;
	}

	public void setDatePl(Date datePl) {
		this.datePl = datePl;
	}

	public Date getDateShipment() {
		return this.dateShipment;
	}

	public void setDateShipment(Date dateShipment) {
		this.dateShipment = dateShipment;
	}

	public String getFreightForwarding() {
		return this.freightForwarding;
	}

	public void setFreightForwarding(String freightForwarding) {
		this.freightForwarding = freightForwarding;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getPayed() {
		return this.payed;
	}

	public void setPayed(String payed) {
		this.payed = payed;
	}

	public String getScheduleSo() {
		return this.scheduleSo;
	}

	public void setScheduleSo(String scheduleSo) {
		this.scheduleSo = scheduleSo;
	}

	public Integer getShipmentNo() {
		return this.shipmentNo;
	}

	public void setShipmentNo(Integer shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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


	public OrderInfoDTO getOrderInfoDTO() {
		return orderInfoDTO;
	}

	public void setOrderInfoDTO(OrderInfoDTO orderInfoDTO) {
		this.orderInfoDTO = orderInfoDTO;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Date getDateShipmentStart() {
		return dateShipmentStart;
	}

	public void setDateShipmentStart(Date dateShipmentStart) {
		this.dateShipmentStart = dateShipmentStart;
	}

	public Date getDateShipmentEnd() {
		return dateShipmentEnd;
	}

	public void setDateShipmentEnd(Date dateShipmentEnd) {
		this.dateShipmentEnd = dateShipmentEnd;
	}

	public String getDateShipmentMonth() {
		return dateShipmentMonth;
	}

	public void setDateShipmentMonth(String dateShipmentMonth) {
		this.dateShipmentMonth = dateShipmentMonth;
	}

}