package com.apical.oddm.facade.sale.saleInfo.cmd;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class SaleInfoCmd {

	private Integer id;

	private String currency;

	private String productType;
	
	private String description;

	private Integer orderId;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	private Integer quantitySale; //销售数量

	private Integer spare;

	private String specification;

	private Timestamp timestamp;

	private BigDecimal unitPrice;

	private Timestamp updatetime;

	/***********订单相关***********/
	private Integer merchandiserId;
	private Integer sellerId;
	private String merchandiser;//所属跟单
	private String seller; //所属业务
	private String clientName; //客户名称
	private String clientNameCode; //客户名称
	private String productFactory;//工厂机型
	private String productClient;//工厂机型
	private Date dateDelivery;//交货日期
	private Date dateOrder; //下单时间
	private String orderNo;
	private String district; //国家地区
	private Integer quantity; //订单数量
	private String dateOrderStart;//下单时间，供传查询用
	private String dateOrderEnd;//下单时间，供传查询用
	private String dateOrderMonth; //月份
	
	public SaleInfoCmd() {
	}

	public SaleInfoCmd(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductType() {
		return productType;
	}

	public String getDateOrderStart() {
		return dateOrderStart;
	}

	public void setDateOrderStart(String dateOrderStart) {
		this.dateOrderStart = dateOrderStart;
	}

	public String getDateOrderEnd() {
		return dateOrderEnd;
	}

	public void setDateOrderEnd(String dateOrderEnd) {
		this.dateOrderEnd = dateOrderEnd;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public Integer getQuantitySale() {
		return quantitySale;
	}

	public void setQuantitySale(Integer quantitySale) {
		this.quantitySale = quantitySale;
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

	@Override
	public String toString() {
		return "SaleInfoCmd [id=" + id + ", currency=" + currency
				+ ", productType=" + productType + ", description=" + description
				+ ", orderId=" + orderId + ", quantitySale=" + quantitySale
				+ ", spare=" + spare + ", specification=" + specification
				+ ", timestamp=" + timestamp + ", unitPrice=" + unitPrice
				+ ", updatetime=" + updatetime + ", merchandiserId="
				+ merchandiserId + ", sellerId=" + sellerId + ", merchandiser="
				+ merchandiser + ", seller=" + seller + ", clientName="
				+ clientName + ", clientNameCode=" + clientNameCode
				+ ", productFactory=" + productFactory + ", dateDelivery="
				+ dateDelivery + ", dateOrder=" + dateOrder + ", orderNo="
				+ orderNo + ", district=" + district + ", quantity=" + quantity
				+ ", dateOrderStart=" + dateOrderStart + ", dateOrderEnd="
				+ dateOrderEnd + "]";
	}

	public String getDateOrderMonth() {
		return dateOrderMonth;
	}

	public void setDateOrderMonth(String dateOrderMonth) {
		this.dateOrderMonth = dateOrderMonth;
	}

	public String getProductClient() {
		return productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

}