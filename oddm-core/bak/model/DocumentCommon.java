package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


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
	private int id;

	@Column(name="name_mt")
	private String nameMt;

	private String path;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadtime;

	public DocumentCommon() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameMt() {
		return this.nameMt;
	}

	public void setNameMt(String nameMt) {
		this.nameMt = nameMt;
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

}