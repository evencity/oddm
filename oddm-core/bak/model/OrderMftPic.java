package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the order_mft_pic database table.
 * 
 */
@Entity
@Table(name="order_mft_pic")
@NamedQuery(name="OrderMftPic.findAll", query="SELECT o FROM OrderMftPic o")
public class OrderMftPic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="mt_code")
	private String mtCode;

	private short orderby;

	@Column(name="pic_path")
	private String picPath;

	private Timestamp timestamp;

	private Timestamp updatetime;

	//bi-directional many-to-one association to OrderManufacture
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mft_id")
	private OrderManufacture orderManufacture;

	public OrderMftPic() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMtCode() {
		return this.mtCode;
	}

	public void setMtCode(String mtCode) {
		this.mtCode = mtCode;
	}

	public short getOrderby() {
		return this.orderby;
	}

	public void setOrderby(short orderby) {
		this.orderby = orderby;
	}

	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public OrderManufacture getOrderManufacture() {
		return this.orderManufacture;
	}

	public void setOrderManufacture(OrderManufacture orderManufacture) {
		this.orderManufacture = orderManufacture;
	}

}