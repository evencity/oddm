package com.apical.oddm.core.model.sys;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.apical.oddm.core.model.base.IdEntity;

/**
 * The persistent class for the sys_notice database table.
 * 
 */
@Entity
@Table(name = "sys_notice")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysNotice extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -8481139521697105074L;

	@Lob
	private String content;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date pubdate;

	private Integer state;

	private Timestamp timestamp;

	private String title;

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPubdate() {
		return this.pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
