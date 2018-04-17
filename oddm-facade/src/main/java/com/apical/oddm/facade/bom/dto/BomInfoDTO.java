package com.apical.oddm.facade.bom.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Description;


public class BomInfoDTO {

	private Integer id;

	private String brand;

	private String description;

	private String materialCode;

	private String productName;

	private String specification;

	private String state;

	private String timestamp;
	
	private Date date;

	private String title;

	private String updatetime;
	
	private String productFactory;//工厂机型

	private String maker;

	private String version;
	
	private String number;//订单号
	
	private String quantity;//数量
	
	private String sort;//分类 
	
	private Integer usageAmount1;

	private Integer usageAmount2;
	
	private String orderNo;
	
	private String type;
	
	private String count;
	
	private List<BomMaterialRefDTO> bomMaterialRefs = new ArrayList<BomMaterialRefDTO>();

	//联系人
	private String cellphone;

	private String company;

	private String contacts;

	private String email;

	private String fax;

	private String telphone;
	
	@Override
	public String toString() {
		return "BomInfoDTO [id=" + id + ", brand=" + brand + ", description="
				+ description + ", materialCode=" + materialCode
				+ ", productName=" + productName + ", specification="
				+ specification + ", state=" + state + ", timestamp="
				+ timestamp + ", date=" + date + ", title=" + title
				+ ", updatetime=" + updatetime + ", maker=" + maker
				+ ", version=" + version + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Description(value="状态")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Description(value="创建时间")
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Description(value="标题")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Description(value="更新时间")
	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Description(value="制作人")
	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}
	@Description(value="版本")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<BomMaterialRefDTO> getBomMaterialRefs() {
		return bomMaterialRefs;
	}

	public void setBomMaterialRefs(List<BomMaterialRefDTO> bomMaterialRefs) {
		this.bomMaterialRefs = bomMaterialRefs;
	}

	public Integer getUsageAmount1() {
		return usageAmount1;
	}

	public void setUsageAmount1(Integer usageAmount1) {
		this.usageAmount1 = usageAmount1;
	}

	public Integer getUsageAmount2() {
		return usageAmount2;
	}

	public void setUsageAmount2(Integer usageAmount2) {
		this.usageAmount2 = usageAmount2;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Description(value="品牌")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	@Description(value="描述")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Description(value="物料编码")
	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@Description(value="品名")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Description(value="规格型号及封装")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	@Description(value="日期")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProductFactory() {
		return productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

}
