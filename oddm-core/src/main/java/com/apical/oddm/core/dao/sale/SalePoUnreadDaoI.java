package com.apical.oddm.core.dao.sale;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.sale.SalePoUnread;

/**
 * 内部订单未读表dao操作接口
 * @author lgx
 * 2016-12-8
 */
public interface SalePoUnreadDaoI extends BaseDaoI<SalePoUnread> {

	/**
	 * 返回用户id
	 * @param poId
	 * @return 
	 */
	public List<Object> listByPoId(int poId);
	/**
	 * 删除未读内部订单表记录
	 * @param userId
	 * @param poId
	 */
	public void delete(int userId, int poId);
	
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
	 * 删除所有含此内部订单id的记录，当删除内部订单的时候使用
	 * @param poId
	 */
	public void deleteAllPoId(int poId);
}
