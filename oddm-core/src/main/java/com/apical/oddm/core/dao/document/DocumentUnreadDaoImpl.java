package com.apical.oddm.core.dao.document;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.document.DocumentUnread;
import com.apical.oddm.core.model.page.SystemContext;

@Repository("documentUnreadDao")
public class DocumentUnreadDaoImpl extends BaseDaoImpl<DocumentUnread> implements DocumentUnreadDaoI {

	@Override
	public void delete(int userId, int docId) {
		String sql = "delete from document_unread where doc_id="+docId+" and user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllUserId(int userId) {
		String sql = "delete from document_unread where user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllDocId(int docId) {
		String sql = "delete from document_unread where doc_id="+docId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteMonth(int month) {
		String sql = "delete from document_unread where updatetime<DATE_SUB(NOW(), INTERVAL "+month+" MONTH)";
		this.updateBySql(sql);
	}

	@Override
	public List<Object> listByDocId(int docId) {
		String sql = "select t.user_id from document_unread t where t.doc_id="+docId;;
		SystemContext.setPageSize(Integer.MAX_VALUE);
		return this.listPageSql(sql, null, null, null, false);
	}

}
