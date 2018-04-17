package com.apical.oddm.facade.manufacture.dto;

import java.io.Serializable;

public class ManufacturePackageTitleDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Integer seq;

	private String version;

	private ManufacturePackageDTO manufacturePackageDTO;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ManufacturePackageDTO getManufacturePackageDTO() {
		return manufacturePackageDTO;
	}

	public void setManufacturePackageDTO(ManufacturePackageDTO manufacturePackageDTO) {
		this.manufacturePackageDTO = manufacturePackageDTO;
	}
}
