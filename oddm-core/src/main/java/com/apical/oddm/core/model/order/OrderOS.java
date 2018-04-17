package com.apical.oddm.core.model.order;

import java.io.Serializable;
import java.sql.Timestamp;

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
import javax.persistence.UniqueConstraint;



/**
 * The persistent class for the order_os database table.
 * 
 */
@Entity
@Table(name="order_os", uniqueConstraints = {@UniqueConstraint(columnNames="order_id")})
@NamedQuery(name="OrderOS.findAll", query="SELECT o FROM OrderOS o")
public class OrderOS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="boot_logo")
	private String bootLogo;

	private String gui;

	private Integer iscustom;

	private Integer isrepeat;

	@Column(name="lang_default")
	private String langDefault;//默认语言

	@Column(name="lang_os")
	private String langOs; //系统语言

	@Column(name="pre_file")
	private String preFile;

	@Column(name="pre_map")
	private String preMap;

	private String timezone;
	
	private String version;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	private String uuid;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	public OrderOS() {
	}

	public OrderOS(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public Integer getIscustom() {
		return this.iscustom;
	}

	public void setIscustom(Integer iscustom) {
		this.iscustom = iscustom;
	}

	public Integer getIsrepeat() {
		return this.isrepeat;
	}

	public void setIsrepeat(Integer isrepeat) {
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}