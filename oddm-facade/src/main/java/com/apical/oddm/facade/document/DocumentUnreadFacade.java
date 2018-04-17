package com.apical.oddm.facade.document;


/**
 * 文档未读信息
 * @author lgx
 * 2016-12-14
 */
public interface DocumentUnreadFacade {
	
	/**
	 * 批量添加文档未读取记录。审核通过后调用
	 * @param currUserId 当前用户id
	 * @param docId
	 */
	public void addDocUnreadBatch(int currUserId, int docId);
}
