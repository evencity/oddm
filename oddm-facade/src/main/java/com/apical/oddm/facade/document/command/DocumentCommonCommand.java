package com.apical.oddm.facade.document.command;

import java.io.Serializable;
import java.util.Date;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午4:10:35 
 * @version 1.0 
 */

public class DocumentCommonCommand implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer version;
	
	private String nameMt;

	private String path;

	private Date uploadtime;

	private Integer state;

	private String description;
	
	
	@Override
	public String toString() {
		return "DocumentCommonCommand [id=" + id + ", version=" + version + ", nameMt=" + nameMt + ", path=" + path + ", uploadtime=" + uploadtime + ", state=" + state
				+ ", description=" + description + "]";
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameMt() {
		return nameMt;
	}

	public void setNameMt(String nameMt) {
		this.nameMt = nameMt;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getUploadtime() {
		return uploadtime;
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
	
}
