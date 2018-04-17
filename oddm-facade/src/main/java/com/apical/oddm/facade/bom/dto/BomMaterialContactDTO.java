package com.apical.oddm.facade.bom.dto;

import java.io.Serializable;

import org.springframework.context.annotation.Description;

/**
 * 物料联系人bom_material_contacts
 * @author 
 *
 */
public class BomMaterialContactDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String cellphone;

	private String company;

	private String contacts;

	private String email;

	private String fax;

	private String mtlCode;

	private String telphone;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Description(value="手机号")
	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	@Description(value="厂家")
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	@Description(value="姓名")
	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	@Description(value="邮箱")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Description(value="传真")
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMtlCode() {
		return this.mtlCode;
	}

	public void setMtlCode(String mtlCode) {
		this.mtlCode = mtlCode;
	}
	@Description(value="座机号")
	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

}
