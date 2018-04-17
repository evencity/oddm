package com.apical.oddm.core.model.sale;

import java.io.Serializable;

import javax.persistence.*;

import com.apical.oddm.core.model.order.OrderInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the sale_out database table.
 * 
 */
@Entity
@Table(name="sale_out")
@NamedQuery(name="SaleOut.findAll", query="SELECT s FROM SaleOut s")
public class SaleOut implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="order_id", updatable=false, insertable=false)
	private Integer orderId;
	
	private BigDecimal amount;

	private String currency;

	@Temporal(TemporalType.DATE)
	@Column(name="date_ci")
	private Date dateCi;

	@Temporal(TemporalType.DATE)
	@Column(name="date_inspection")
	private Date dateInspection;

	@Temporal(TemporalType.DATE)
	@Column(name="date_pl")
	private Date datePl;

	@Temporal(TemporalType.DATE)
	@Column(name="date_shipment")
	private Date dateShipment;

	@Column(name="freight_forwarding")
	private String freightForwarding;

	@Column(name="group_")
	private String group_;

	private String payed;

	@Column(name="schedule_so")
	private String scheduleSo;

	@Column(name="shipment_no")
	private Integer shipmentNo;

	private String status;

	private Timestamp timestamp;

	private Timestamp updatetime;

	@Transient
	private String productType;
	
	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateShipmentStart;//下单时间，供传查询用
	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateShipmentEnd;//下单时间，供传查询用
	
	private String dateShipmentMonth;//下单时间，供传查询用

	public SaleOut() {
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

	public String getGroup_() {
		return this.group_;
	}

	public void setGroup_(String group) {
		this.group_ = group;
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

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
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