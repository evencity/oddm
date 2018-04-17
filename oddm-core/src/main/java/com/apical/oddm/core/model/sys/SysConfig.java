package com.apical.oddm.core.model.sys;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the sys_config database table.
 * 
 */
@Entity
@Table(name="sys_config")
public class SysConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;

	@Id
	@Column(name="key_")
	private String key;

	private String value;

	public SysConfig() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}