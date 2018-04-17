package com.apical.oddm.core.model.document;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.apical.oddm.core.model.order.OrderInfo;


/**
 * The persistent class for the document database table.
 * 
 */
@Entity
@Table(name="document")
@NamedQuery(name="Document.findAll", query="SELECT d FROM Document d")
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="download_count")
	private Integer downloadCount;

	@Column(name="name_mt")
	private String nameMt;

	@Column(name="material_code")
	private String codeMt;

	private String path;

	private Integer state;

	@Column(name="user_id")
	private Integer userId;
	
	private Integer type;

	private Integer version;
	
	@Transient
	private Integer unread; //订单1未读，供传查询用，页面只显示1未读即可
	
	@Transient
	private String orderNo;
	
	@Transient
	private String productClient; //客户机型

	@Transient
	private String productFactory;//工厂机型
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false, insertable=false)
	private Date uploadtime;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;
	
	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orderid")
	private OrderInfo orderInfo;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="orderid", updatable=false, insertable=false)
	private Integer orderId;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Document() {
	}

	public Document(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUnread() {
		if (unread != null) return 1;
		return unread;
	}

	public void setUnread(Integer unread) {
		this.unread = unread;
	}
	/**
	 * 仅供hql语句拼接查询用
	 * @return
	 */
	public boolean isUnread() {
		if (unread != null && unread == 1) {
			return true;
		}
		return false;
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

	public Integer getDownloadCount() {
		return this.downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getNameMt() {
		return this.nameMt;
	}

	public void setNameMt(String nameMt) {
		this.nameMt = nameMt;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}