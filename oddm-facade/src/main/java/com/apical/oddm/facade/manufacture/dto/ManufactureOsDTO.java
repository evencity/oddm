package com.apical.oddm.facade.manufacture.dto;

import java.io.Serializable;

public class ManufactureOsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private String notice;

	private String otherRequire;

	private String preFile;

	private String updatetime;

	private String uuid;

	private String versionApp;

	private String versionCore;

	private String versionFirmware;

	private String versionMap;
	
	public Integer getId() {
		return id;
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

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}
