package com.apical.oddm.application.document;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.document.DocumentUnread;
import com.apical.oddm.core.model.sys.User;


/**
 * 文档未读表操作接口
 * @author lgx
 * 2016-12-8
 */
public interface DocumentUnreadServiceI extends BaseServiceI<DocumentUnread> {

	/**
	 * 删除未读文档表记录。下载成功后调用
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
	 * 批量添加文档未读取记录。审核通过后调用
	 * @param currUserId 当前用户id
	 * @param users用户列表
	 * @param docId
	 */
	public void addDocUnreadBatch(int currUserId, List<User> users,int docId);
}
