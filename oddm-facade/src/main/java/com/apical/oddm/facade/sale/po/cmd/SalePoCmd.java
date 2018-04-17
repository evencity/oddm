package com.apical.oddm.facade.sale.po.cmd;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Description;

public class SalePoCmd {
	private Integer id;

	private String company;
	
	private String address;
	
	private String tel;
	
	private String fax;

	private String homepage;
	
	private String piNo;//备注

	private Date dateExaminePre; //预计验货日期

	private String description;//备注

	private String currency;

	private String maker;//拟制
	
	private String approver;//批准
	
	private Integer approverId;//批准

	private String pm;//产品经理

	private Integer state;

	private Integer orderId;
	
	private Integer merchandiserId;

	private Integer sellerId;

	private String merchandiser;//所属跟单

	private String seller; //所属业务
	
	private String clientName; //客户名称
	
	private String clientNameCode;//客户编码

	private String productFactory;//工厂机型
	
	private Date dateDelivery;//交货日期

	private Date dateOrder; //下单时间
	
	private String orderNo;
	
	private String district;
	
	private String productType;

	private List<SalePoListCmd> salePoListCmd;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public SalePoCmd() {
	}

	public SalePoCmd(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Description(value="预计验货日期")
	public Date getDateExaminePre() {
		return this.dateExaminePre;
	}

	public void setDateExaminePre(Date dateExaminePre) {
		this.dateExaminePre = dateExaminePre;
	}

	@Description(value="备注描述")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(value="拟制")
	public String getMaker() {
		return this.maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	@Description(value="产品经理")
	public String getPm() {
		return this.pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Description(value="币种")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Description(value="批准")
	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getMerchandiserId() {
		return merchandiserId;
	}

	public void setMerchandiserId(Integer merchandiserId) {
		this.merchandiserId = merchandiserId;
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

	public List<SalePoListCmd> getSalePoListCmd() {
		return salePoListCmd;
	}

	public void setSalePoListCmd(List<SalePoListCmd> salePoListCmd) {
		this.salePoListCmd = salePoListCmd;
	}

	public String getClientNameCode() {
		return clientNameCode;
	}

	public void setClientNameCode(String clientNameCode) {
		this.clientNameCode = clientNameCode;
	}

	@Description(value="PO编号")
	public String getPiNo() {
		return piNo;
	}

	public void setPiNo(String piNo) {
		this.piNo = piNo;
	}

	public String getProductFactory() {
		return productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "SalePoCmd [id="
				+ id
				+ ", company="
				+ company
				+ ", address="
				+ address
				+ ", tel="
				+ tel
				+ ", fax="
				+ fax
				+ ", homepage="
				+ homepage
				+ ", poNo="
				+ piNo
				+ ", dateExamine="
				+ dateExaminePre
				+ ", description="
				+ description
				+ ", currency="
				+ currency
				+ ", maker="
				+ maker
				+ ", approver="
				+ approver
				+ ", pm="
				+ pm
				+ ", state="
				+ state
				+ ", orderId="
				+ orderId
				+ ", merchandiserId="
				+ merchandiserId
				+ ", sellerId="
				+ sellerId
				+ ", merchandiser="
				+ merchandiser
				+ ", seller="
				+ seller
				+ ", clientName="
				+ clientName
				+ ", clientNameCode="
				+ clientNameCode
				+ ", productFactory="
				+ productFactory
				+ ", dateDelivery="
				+ dateDelivery
				+ ", dateOrder="
				+ dateOrder
				+ ", orderNo="
				+ orderNo
				+ ", salePoListCmd="
				+ (salePoListCmd != null ? salePoListCmd.subList(0,
						Math.min(salePoListCmd.size(), maxLen)) : null) + "]";
	}

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
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