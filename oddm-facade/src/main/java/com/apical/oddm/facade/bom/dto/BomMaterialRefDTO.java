package com.apical.oddm.facade.bom.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.context.annotation.Description;
/**
 * BOM表单信息与BOM物料关联
 * @author 
 *
 */
public class BomMaterialRefDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String brand;

	private String description;

	private String materialCode;

	private Integer seq;

	private String productName;

	private String specification;

	private Integer type;

	private Timestamp updatetime;

	private Integer usageAmount1;

	private Integer usageAmount2;
	
	private String usageAmount;
	
	private String contacts;
	
	private Integer contactId;
	
	private String cellphone;
	
	private String company;
	
	private String email;
	
	private String fax;
	
	private String telphone;
	
	private BomMaterialContactDTO materialContactDTO;
	
	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public BomMaterialContactDTO getMaterialContactDTO() {
		return materialContactDTO;
	}

	public void setMaterialContactDTO(BomMaterialContactDTO materialContactDTO) {
		this.materialContactDTO = materialContactDTO;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Description(value="品牌")
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	@Description(value="描述")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Description(value="物料编码")
	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	@Description(value="品名")
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Description(value="规格型号及封装")
	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	
	public Integer getUsageAmount1() {
		return this.usageAmount1;
	}

	public void setUsageAmount1(Integer usageAmount1) {
		this.usageAmount1 = usageAmount1;
	}
	public Integer getUsageAmount2() {
		return this.usageAmount2;
	}

	public void setUsageAmount2(Integer usageAmount2) {
		this.usageAmount2 = usageAmount2;
	}
	@Description(value="用量")
	public String getUsageAmount() {
		return usageAmount;
	}

	public void setUsageAmount(String usageAmount) {
		this.usageAmount = usageAmount;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
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
