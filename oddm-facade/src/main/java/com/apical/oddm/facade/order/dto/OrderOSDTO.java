package com.apical.oddm.facade.order.dto;

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
public class OrderOSDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String bootLogo;

	private String gui;

	private Integer iscustom;

	private Integer isrepeat;

	private String langDefault;

	private String langOs;

	private String preFile;

	private String preMap;

	private String timezone;

	private Timestamp updatetime;

	private String uuid;

	private String orderId;
	
	private String version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBootLogo() {
		return bootLogo;
	}

	public void setBootLogo(String bootLogo) {
		this.bootLogo = bootLogo;
	}

	public String getGui() {
		return gui;
	}

	public void setGui(String gui) {
		this.gui = gui;
	}

	public Integer getIscustom() {
		return iscustom;
	}

	public void setIscustom(Integer iscustom) {
		this.iscustom = iscustom;
	}

	public Integer getIsrepeat() {
		return isrepeat;
	}

	public void setIsrepeat(Integer isrepeat) {
		this.isrepeat = isrepeat;
	}

	public String getLangDefault() {
		return langDefault;
	}

	public void setLangDefault(String langDefault) {
		this.langDefault = langDefault;
	}

	public String getLangOs() {
		return langOs;
	}

	public void setLangOs(String langOs) {
		this.langOs = langOs;
	}

	public String getPreFile() {
		return preFile;
	}

	public void setPreFile(String preFile) {
		this.preFile = preFile;
	}

	public String getPreMap() {
		return preMap;
	}

	public void setPreMap(String preMap) {
		this.preMap = preMap;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderOS [id=" + id + ", bootLogo=" + bootLogo + ", gui=" + gui + ", iscustom=" + iscustom + ", isrepeat=" + isrepeat + ", langDefault=" + langDefault + ", langOs="
				+ langOs + ", preFile=" + preFile + ", preMap=" + preMap + ", timezone=" + timezone + ", updatetime=" + updatetime + ", uuid=" + uuid + ", orderId=" + orderId
				+ "]";
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}