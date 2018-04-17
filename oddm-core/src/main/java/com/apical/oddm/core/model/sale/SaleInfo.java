package com.apical.oddm.core.model.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.apical.oddm.core.model.order.OrderInfo;


/**
 * The persistent class for the sale_info database table.
 * 
 */
@Entity
@Table(name="sale_info")
@NamedQuery(name="SaleInfo.findAll", query="SELECT s FROM SaleInfo s")
public class SaleInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	private String currency;

	private String description;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="order_id", updatable=false, insertable=false)
	private Integer orderId;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name="quantity_sale",nullable=false)
	private Integer quantitySale; //销售数量

	private Integer spare;

	@Column()
	private String specification;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(name="unit_price")
	private BigDecimal unitPrice;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	/***********订单相关***********/
	@Transient
	private Integer merchandiserId;
	@Transient
	private Integer sellerId;
	@Transient
	private String merchandiser;//所属跟单
	@Transient
	private String seller; //所属业务
	@Transient
	private String clientName; //客户名称
	@Transient
	private String clientNameCode; //客户名称
	@Transient
	private String productFactory;//工厂机型
	@Transient
	private String productClient;//工厂机型
	@Transient
	private Date dateDelivery;//交货日期
	@Transient
	private Date dateOrder; //下单时间
	@Transient
	private String orderNo;
	@Transient
	private Integer quantity;
	@Transient
	private String district;
	@Transient
	private String productType;
	
	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateOrderStart;//下单时间，供传查询用
	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateOrderEnd;//下单时间，供传查询用
	
	public SaleInfo() {
	}

	public SaleInfo(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateOrderStart() {
		return dateOrderStart;
	}

	public void setDateOrderStart(Date dateOrderStart) {
		this.dateOrderStart = dateOrderStart;
	}

	public Date getDateOrderEnd() {
		return dateOrderEnd;
	}

	public void setDateOrderEnd(Date dateOrderEnd) {
		this.dateOrderEnd = dateOrderEnd;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public String getProductClient() {
		return productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

	public Integer getQuantitySale() {
		return quantitySale;
	}

	public void setQuantitySale(Integer quantitySale) {
		this.quantitySale = quantitySale;
	}

	public Integer getSpare() {
		return this.spare;
	}

	public void setSpare(Integer spare) {
		this.spare = spare;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
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

	public Integer getMerchandiserId() {
		return merchandiserId;
	}

	public void setMerchandiserId(Integer merchandiserId) {
		this.merchandiserId = merchandiserId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getMerchandiser() {
		return merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientNameCode() {
		return clientNameCode;
	}

	public void setClientNameCode(String clientNameCode) {
		this.clientNameCode = clientNameCode;
	}

	public String getProductFactory() {
		return productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public Date getDateDelivery() {
		return dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
}