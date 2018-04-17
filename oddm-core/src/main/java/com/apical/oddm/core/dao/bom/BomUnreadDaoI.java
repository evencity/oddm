package com.apical.oddm.core.dao.bom;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.bom.BomUnread;

/**
 * BOM未读表dao操作接口
 * @author lgx
 * 2016-12-8
 */
public interface BomUnreadDaoI extends BaseDaoI<BomUnread> {

	/**
	 * 返回用户id
	 * @param bomId
	 * @return 
	 */
	public List<Object> listByBomId(int bomId);
	/**
	 * 删除未读BOM表记录
	 * @param userId
	 * @param bomId
	 */
	public void delete(int userId, int bomId);
	
	/**
	 * 删除 几个月前的
	 */
	public void deleteMonth(int month);
	
	/**
	 * 删除所有含此用户id的记录，当删除用户的时候使用
	 * @param userId
	 */
	public void deleteAllUserId(int userId);
	
	/**
	 * 删除所有含此BOMid的记录，当删除BOM的时候使用
	 * @param bomId
	 */
	public void deleteAllBomId(int bomId);
}
