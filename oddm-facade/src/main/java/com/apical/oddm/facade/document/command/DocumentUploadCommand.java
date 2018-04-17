package com.apical.oddm.facade.document.command;

import java.io.Serializable;
import java.util.Date;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午4:06:23 
 * @version 1.0 
 */

public class DocumentUploadCommand implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String start;
	
	private String end;
	
	private String documentName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	@Override
	public String toString() {
		return "DocumentUploadCommand [id=" + id + ", start=" + start + ", end=" + end + ", documentName=" + documentName + "]";
	}
	
}
