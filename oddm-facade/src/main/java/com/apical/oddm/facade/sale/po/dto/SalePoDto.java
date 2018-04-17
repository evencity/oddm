package com.apical.oddm.facade.sale.po.dto;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class SalePoDto {
	private Integer id;

	private Integer unread; //1未读，供传查询用，页面只显示1未读即可

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
	
	private Integer approverId;
	
	private String approver;//批准
	
	private String pm;//产品经理

	private Timestamp timestamp;

	private Timestamp updatetime;

	private Integer state;
	
	private Set<SalePoListDto> salePoListsDto;

	private Integer orderId;
	
	private String productType;

	/***********订单相关***********/
	private Integer merchandiserId;
	private Integer sellerId;
	private String merchandiser;//所属跟单
	private String seller; //所属业务
	private String clientName; //客户名称
	private String clientNameCode; //客户名称
	private String productFactory;//工厂机型
	private Date dateDelivery;//交货日期
	private Date dateOrder; //下单时间
	private String orderNo;
	private String district;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public SalePoDto() {
	}

	public SalePoDto(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateExaminePre() {
		return this.dateExaminePre;
	}

	public void setDateExaminePre(Date dateExaminePre) {
		this.dateExaminePre = dateExaminePre;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaker() {
		return this.maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getPm() {
		return this.pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Set<SalePoListDto> getSalePoListsDto() {
		return salePoListsDto;
	}

	public void setSalePoListsDto(Set<SalePoListDto> salePoListsDto) {
		this.salePoListsDto = salePoListsDto;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "SalePoDto [id="
				+ id
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
				+ ", timestamp="
				+ timestamp
				+ ", updatetime="
				+ updatetime
				+ ", state="
				+ state
				+ ", salePoListsDto="
				+ (salePoListsDto != null ? toString(salePoListsDto, maxLen)
						: null) + ", orderId=" + orderId + ", merchandiserId="
				+ merchandiserId + ", sellerId=" + sellerId + ", merchandiser="
				+ merchandiser + ", seller=" + seller + ", clientName="
				+ clientName + ", dateDelivery=" + dateDelivery
				+ ", dateOrder=" + dateOrder + ", orderNo=" + orderNo + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	public String getClientNameCode() {
		return clientNameCode;
	}

	public void setClientNameCode(String clientNameCode) {
		this.clientNameCode = clientNameCode;
	}

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

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

	public Integer getUnread() {
		return unread;
	}

	public void setUnread(Integer unread) {
		this.unread = unread;
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