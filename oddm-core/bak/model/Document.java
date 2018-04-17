package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the document database table.
 * 
 */
@Entity
@NamedQuery(name="Document.findAll", query="SELECT d FROM Document d")
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="download_count")
	private short downloadCount;

	@Column(name="name_mt")
	private String nameMt;

	private String path;

	@Column(name="product_client")
	private String productClient;

	@Column(name="product_factory")
	private String productFactory;

	private byte state;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadtime;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orderid")
	private OrderInfo orderInfo;

	public Document() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public short getDownloadCount() {
		return this.downloadCount;
	}

	public void setDownloadCount(short downloadCount) {
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

	public String getProductClient() {
		return this.productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

	public String getProductFactory() {
		return this.productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
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

}