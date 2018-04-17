package com.apical.oddm.application.bom;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.bom.BomUnread;
import com.apical.oddm.core.model.sys.User;


/**
 * BOM未读表操作接口
 * @author lgx
 * 2016-12-8
 */
public interface BomUnreadServiceI extends BaseServiceI<BomUnread> {

	/**
	 * 删除未读BOM表记录
	 * @param userId
	 * @param bomId
	 */
	public void delete(int userId, int bomId);

	/**
	 * 删除 几个月前的
	 * @param month
	 */
	public void deleteMonth(int month);

	/**
	 * 批量添加BOM未读取记录
	 * @param currUserId 当前用户id
	 * @param users用户列表
	 * @param bomId
	 */
	public void addBomUnreadBatch(int currUserId, List<User> users,int bomId);
	
	/**
	 * 增加或则更新
	 * @param bomUnread
	 */
	public void saveOrUpdate(BomUnread bomUnread);
}
