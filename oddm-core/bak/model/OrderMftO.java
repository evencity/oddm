package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the order_mft_os database table.
 * 
 */
@Entity
@Table(name="order_mft_os")
@NamedQuery(name="OrderMftO.findAll", query="SELECT o FROM OrderMftO o")
public class OrderMftO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="boot_logo_path")
	private String bootLogoPath;

	private String gui;

	@Column(name="lang_default")
	private String langDefault;

	@Column(name="lang_os")
	private String langOs;

	@Column(name="pre_file")
	private String preFile;

	@Column(name="pre_map")
	private String preMap;

	private String timezone;

	private String uuid;

	//bi-directional many-to-one association to OrderManufacture
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mftid")
	private OrderManufacture orderManufacture;

	public OrderMftO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBootLogoPath() {
		return this.bootLogoPath;
	}

	public void setBootLogoPath(String bootLogoPath) {
		this.bootLogoPath = bootLogoPath;
	}

	public String getGui() {
		return this.gui;
	}

	public void setGui(String gui) {
		this.gui = gui;
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

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public OrderManufacture getOrderManufacture() {
		return this.orderManufacture;
	}

	public void setOrderManufacture(OrderManufacture orderManufacture) {
		this.orderManufacture = orderManufacture;
	}

}