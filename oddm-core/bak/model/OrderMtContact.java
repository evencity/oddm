package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the order_mt_contacts database table.
 * 
 */
@Entity
@Table(name="order_mt_contacts")
public class OrderMtContact implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cc;

	@Column(name="from_")
	private String from;

	private int title;

	@Column(name="to_company")
	private String toCompany;

	@Column(name="to_member")
	private String toMember;

	@Column(name="to_phone")
	private String toPhone;

	public OrderMtContact() {
	}

	public String getCc() {
		return this.cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public int getTitle() {
		return this.title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public String getToCompany() {
		return this.toCompany;
	}

	public void setToCompany(String toCompany) {
		this.toCompany = toCompany;
	}

	public String getToMember() {
		return this.toMember;
	}

	public void setToMember(String toMember) {
		this.toMember = toMember;
	}

	public String getToPhone() {
		return this.toPhone;
	}

	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

}