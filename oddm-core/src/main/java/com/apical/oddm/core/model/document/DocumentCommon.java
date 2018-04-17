package com.apical.oddm.core.model.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the document_common database table.
 * 
 */
@Entity
@Table(name="document_common")
@NamedQuery(name="DocumentCommon.findAll", query="SELECT d FROM DocumentCommon d")
public class DocumentCommon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="name_mt")
	private String nameMt;

	private String path;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadtime;

	private Integer state;

	private Integer version;

	private String description;
	
	public DocumentCommon() {
	}

	public DocumentCommon(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameMt() {
		return this.nameMt;
	}

	public void setNameMt(String nameMt) {
		this.nameMt = nameMt;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}