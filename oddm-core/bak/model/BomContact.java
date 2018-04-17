package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bom_contacts database table.
 * 
 */
@Entity
@Table(name="bom_contacts")
public class BomContact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String cellphone;

	private String company;

	private String contacts;

	private String email;

	private String fax;

	@Column(name="mtl_code")
	private String mtlCode;

	private String telphone;

	public BomContact() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

}