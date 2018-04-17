package com.apical.oddm.core.dao.document;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.document.DocumentUnread;

/**
 * 文档未读表dao操作接口
 * @author lgx
 * 2016-12-8
 */
public interface DocumentUnreadDaoI extends BaseDaoI<DocumentUnread> {

	/**
	 * 返回用户id
	 * @param docId
	 * @return 
	 */
	public List<Object> listByDocId(int docId);
	
	/**
	 * 删除未读文档表记录
	 * @param userId
	 * @param docId
	 */
	public void delete(int userId, int docId);
	
	/**
	 * 删除 几个月前的
	 * @param month
	 */
	public void deleteMonth(int month);
	
	/**
	 * 删除所有含此用户id的记录，当删除用户的时候使用
	 * @param userId
	 */
	public void deleteAllUserId(int userId);
	
	/**
	 * 删除所有含此文档id的记录，当删除文档的时候使用
	 * @param docId
	 */
	public void deleteAllDocId(int docId);
}
