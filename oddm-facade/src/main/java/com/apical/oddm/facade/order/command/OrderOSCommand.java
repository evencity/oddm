package com.apical.oddm.facade.order.command;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.context.annotation.Description;


/**
 * The persistent class for the order_os database table.
 * 
 */
public class OrderOSCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String bootLogo;//开机画面

	private String gui;//GUI

	private Integer iscustom;//是否定制

	private Integer isrepeat;//新/翻单软件

	private String langDefault;//默认语言

	private String langOs;//系统语言

	private String preFile;//预存文件

	private String preMap;//预存地图

	private String timezone;//时区

	private Timestamp updatetime;//更新时间

	private String uuid;//UUID号

	private String version;//软件版本号
	
	private OrderInfoCommand orderInfoCommand;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Description(value="开机画面")
	public String getBootLogo() {
		return bootLogo;
	}

	public void setBootLogo(String bootLogo) {
		this.bootLogo = bootLogo;
	}

	@Description(value="GUI")
	public String getGui() {
		return gui;
	}

	public void setGui(String gui) {
		this.gui = gui;
	}

	@Description(value="是否定制")
	public Integer getIscustom() {
		return iscustom;
	}

	public void setIscustom(Integer iscustom) {
		this.iscustom = iscustom;
	}

	//@Description(value="软件")//这个是创建订单或翻单的时候程序就写好的，没必要判断
	public Integer getIsrepeat() {
		return isrepeat;
	}

	public void setIsrepeat(Integer isrepeat) {
		this.isrepeat = isrepeat;
	}
	
	@Description(value="默认语言")
	public String getLangDefault() {
		return langDefault;
	}

	public void setLangDefault(String langDefault) {
		this.langDefault = langDefault;
	}

	@Description(value="系统语言")
	public String getLangOs() {
		return langOs;
	}

	public void setLangOs(String langOs) {
		this.langOs = langOs;
	}

	@Description(value="预存文件")
	public String getPreFile() {
		return preFile;
	}

	public void setPreFile(String preFile) {
		this.preFile = preFile;
	}

	@Description(value="预存地图")
	public String getPreMap() {
		return preMap;
	}

	public void setPreMap(String preMap) {
		this.preMap = preMap;
	}

	@Description(value="地区时间")
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

	@Description(value="UUID号")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public OrderInfoCommand getOrderInfoCommand() {
		return orderInfoCommand;
	}

	public void setOrderInfoCommand(OrderInfoCommand orderInfoCommand) {
		this.orderInfoCommand = orderInfoCommand;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "OrderOSCommand [id=" + id + ", bootLogo=" + bootLogo + ", gui=" + gui + ", iscustom=" + iscustom
				+ ", isrepeat=" + isrepeat + ", langDefault=" + langDefault + ", langOs=" + langOs + ", preFile="
				+ preFile + ", preMap=" + preMap + ", timezone=" + timezone + ", updatetime=" + updatetime + ", uuid="
				+ uuid + ", version=" + version + ", orderInfoCommand=" + orderInfoCommand + "]";
	}
	
	

	
	
}