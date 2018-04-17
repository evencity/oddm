package com.apical.oddm.core.model.produce;

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
 * The persistent class for the order_mft_os database table.
 * 
 */
@Entity
@Table(name="order_mft_os", uniqueConstraints = {@UniqueConstraint(columnNames="mftid")})
@NamedQuery(name="ManufactureOs.findAll", query="SELECT o FROM ManufactureOs o")
public class ManufactureOs implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String notice;

	@Column(name="other_require")
	private String otherRequire;

	@Column(name="pre_file")
	private String preFile;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	private String uuid;

	@Column(name="version_app")
	private String versionApp;

	@Column(name="version_core")
	private String versionCore;

	@Column(name="version_firmware")
	private String versionFirmware;

	@Column(name="version_map")
	private String versionMap;

	//bi-directional many-to-one association to OrderManufacture
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mftid")
	private Manufacture orderManufacture;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="mftid", updatable=false, insertable=false)
	private Integer mftid;
	
	public ManufactureOs() {
	}

	public ManufactureOs(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotice() {
		return this.notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getOtherRequire() {
		return this.otherRequire;
	}

	public void setOtherRequire(String otherRequire) {
		this.otherRequire = otherRequire;
	}

	public String getPreFile() {
		return this.preFile;
	}

	public void setPreFile(String preFile) {
		this.preFile = preFile;
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

	public String getVersionApp() {
		return this.versionApp;
	}

	public void setVersionApp(String versionApp) {
		this.versionApp = versionApp;
	}

	public String getVersionCore() {
		return this.versionCore;
	}

	public void setVersionCore(String versionCore) {
		this.versionCore = versionCore;
	}

	public String getVersionFirmware() {
		return this.versionFirmware;
	}

	public void setVersionFirmware(String versionFirmware) {
		this.versionFirmware = versionFirmware;
	}

	public String getVersionMap() {
		return this.versionMap;
	}

	public void setVersionMap(String versionMap) {
		this.versionMap = versionMap;
	}

	public Manufacture getOrderManufacture() {
		return this.orderManufacture;
	}

	public void setOrderManufacture(Manufacture orderManufacture) {
		this.orderManufacture = orderManufacture;
	}

	public Integer getMftid() {
		return mftid;
	}

	public void setMftid(Integer mftid) {
		this.mftid = mftid;
	}

}