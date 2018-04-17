package com.apical.oddm.facade.manufacture.command;

import java.io.Serializable;

import org.springframework.context.annotation.Description;

public class ManufactureOsCommand implements Serializable {
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
	
	private Integer manufactureId;
	
	public Integer getManufactureId() {
		return manufactureId;
	}

	public void setManufactureId(Integer manufactureId) {
		this.manufactureId = manufactureId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Description(value="备注")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(value="注意事项")
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	@Description(value="其他软件要求")
	public String getOtherRequire() {
		return otherRequire;
	}

	public void setOtherRequire(String otherRequire) {
		this.otherRequire = otherRequire;
	}

	@Description(value="预存文件")
	public String getPreFile() {
		return preFile;
	}

	public void setPreFile(String preFile) {
		this.preFile = preFile;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Description(value="UUID")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Description(value="应用版本")
	public String getVersionApp() {
		return versionApp;
	}

	public void setVersionApp(String versionApp) {
		this.versionApp = versionApp;
	}

	@Description(value="内核版本")
	public String getVersionCore() {
		return versionCore;
	}

	public void setVersionCore(String versionCore) {
		this.versionCore = versionCore;
	}

	@Description(value="固件版本")
	public String getVersionFirmware() {
		return versionFirmware;
	}

	public void setVersionFirmware(String versionFirmware) {
		this.versionFirmware = versionFirmware;
	}

	@Description(value="地图版本")
	public String getVersionMap() {
		return versionMap;
	}

	public void setVersionMap(String versionMap) {
		this.versionMap = versionMap;
	}

	@Override
	public String toString() {
		return "ManufactureOsCommand [id=" + id + ", description=" + description + ", notice=" + notice + ", otherRequire=" + otherRequire + ", preFile=" + preFile
				+ ", updatetime=" + updatetime + ", uuid=" + uuid + ", versionApp=" + versionApp + ", versionCore=" + versionCore + ", versionFirmware=" + versionFirmware
				+ ", versionMap=" + versionMap + "]";
	}
}
