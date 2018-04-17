package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the order_os database table.
 * 
 */
@Entity
@Table(name="order_os")
@NamedQuery(name="OrderO.findAll", query="SELECT o FROM OrderO o")
public class OrderOS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="boot_logo")
	private String bootLogo;

	private String gui;

	private byte iscustom;

	private byte isrepeat;

	@Column(name="lang_default")
	private String langDefault;

	@Column(name="lang_os")
	private String langOs;

	@Column(name="pre_file")
	private String preFile;

	@Column(name="pre_map")
	private String preMap;

	private String timezone;

	private Timestamp updatetime;

	private String uuid;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	public OrderOS() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBootLogo() {
		return this.bootLogo;
	}

	public void setBootLogo(String bootLogo) {
		this.bootLogo = bootLogo;
	}

	public String getGui() {
		return this.gui;
	}

	public void setGui(String gui) {
		this.gui = gui;
	}

	public byte getIscustom() {
		return this.iscustom;
	}

	public void setIscustom(byte iscustom) {
		this.iscustom = iscustom;
	}

	public byte getIsrepeat() {
		return this.isrepeat;
	}

	public void setIsrepeat(byte isrepeat) {
		this.isrepeat = isrepeat;
	}

	public String getLangDefault() {
		return this.langDefault;
	}

	public void setLangDefault(String langDefault) {
		this.langDefault = langDefault;
	}

	public String getLangOs() {
		return this.langOs;
	}

	public void setLangOs(String langOs) {
		this.langOs = langOs;
	}

	public String getPreFile() {
		return this.preFile;
	}

	public void setPreFile(String preFile) {
		this.preFile = preFile;
	}

	public String getPreMap() {
		return this.preMap;
	}

	public void setPreMap(String preMap) {
		this.preMap = preMap;
	}

	public String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

}