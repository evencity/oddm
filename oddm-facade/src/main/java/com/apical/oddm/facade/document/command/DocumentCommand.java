	package com.apical.oddm.facade.document.command;

import java.io.Serializable;
import java.util.Date;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午4:06:23 
 * @version 1.0 
 */

public class DocumentCommand implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer downloadCount;

	private String nameMt;
	
	private String codeMt;

	private String path;

	private Integer state;

	private Date uploadtime;
	
	private Integer version;

	private Integer orderId;
	
	private String description;
	
	private Integer unread; //订单1未读，供传查询用，页面只显示1未读即可
	
	private String orderNo;
	
	private String productClient; //客户机型

	private String productFactory;//工厂机型
	
	private Integer userId;
	
	private Integer type;
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUnread() {
		return unread;
	}

	public void setUnread(Integer unread) {
		this.unread = unread;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductClient() {
		return productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

	public String getProductFactory() {
		return productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCodeMt() {
		return codeMt;
	}

	public void setCodeMt(String codeMt) {
		this.codeMt = codeMt;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getNameMt() {
		return nameMt;
	}

	public void setNameMt(String nameMt) {
		this.nameMt = nameMt;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "DocumentCommand [id=" + id + ", downloadCount=" + downloadCount + ", nameMt=" + nameMt + ", codeMt=" + codeMt + ", path=" + path + ", state=" + state
				+ ", uploadtime=" + uploadtime + ", version=" + version + ", orderId=" + orderId + ", description=" + description + ", unread=" + unread + ", orderNo=" + orderNo
				+ ", productClient=" + productClient + ", productFactory=" + productFactory + "]";
	}

	
}
