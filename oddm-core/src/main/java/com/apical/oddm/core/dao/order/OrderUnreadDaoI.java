package com.apical.oddm.core.dao.order;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderUnread;

/**
 * 订单未读表dao操作接口
 * @author lgx
 * 2016-12-8
 */
public interface OrderUnreadDaoI extends BaseDaoI<OrderUnread> {

	/**
	 * 返回用户id
	 * @param orderId
	 * @return 
	 */
	public List<Object> listByOrderId(int orderId);
	/**
	 * 删除未读订单表记录
	 * @param userId
	 * @param orderId
	 */
	public void delete(int userId, int orderId);
	
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
	 * 删除所有含此订单id的记录，当删除订单的时候使用
	 * @param orderId
	 */
	public void deleteAllOrderId(int orderId);
}
