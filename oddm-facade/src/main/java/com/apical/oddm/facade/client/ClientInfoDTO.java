package com.apical.oddm.facade.client;

import java.io.Serializable;
import java.sql.Timestamp;


public class ClientInfoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;

	private String bizScope;

	private String brand;

	private String description;

	private String district;

	private String homepage;

	private String name;

	private String nameCode;

	private Timestamp timestamp;

	private String type;

	private Timestamp updatetime;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBizScope() {
		return this.bizScope;
	}

	public void setBizScope(String bizScope) {
		this.bizScope = bizScope;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameCode() {
		return this.nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
}
