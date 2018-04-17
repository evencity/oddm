package com.apical.oddm.facade.encase.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 集装箱信息
 * @author wangtianqun
 *
 */

public class EncaseInfoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String address;

	private String company;

	private String description;

	private String homepage;

	private String name;

	private String encaseDate;

	private String telphone;

	private String timestamp;

	private String unitLength;

	private String unitWeight;

	private String zipcode;
	
	private Long total;//装箱总数
	
	private Long count;//产品总数
	
	private String orderNo;
	
	private List<EncaseListDTO> encaseList = new ArrayList<EncaseListDTO>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncaseDate() {
		return encaseDate;
	}

	public void setEncaseDate(String encaseDate) {
		this.encaseDate = encaseDate;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUnitLength() {
		return unitLength;
	}

	public void setUnitLength(String unitLength) {
		this.unitLength = unitLength;
	}

	public String getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(String unitWeight) {
		this.unitWeight = unitWeight;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<EncaseListDTO> getEncaseList() {
		return encaseList;
	}

	public void setEncaseList(List<EncaseListDTO> encaseList) {
		this.encaseList = encaseList;
	}

	
}
