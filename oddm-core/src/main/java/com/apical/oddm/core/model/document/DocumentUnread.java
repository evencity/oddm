package com.apical.oddm.core.model.document;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="document_unread")
public class DocumentUnread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="doc_id")
	private Integer docId;

	@Id
	@Column(name="user_id")
	private Integer userId;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public DocumentUnread(Integer userId, Integer docId) {
		this.userId = userId;
		this.docId = docId;
	}

	public DocumentUnread() {
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	@Override
	public String toString() {
		return "DocumentUnread [docId=" + docId + ", userId=" + userId + "]";
	}

}
