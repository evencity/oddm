package com.apical.oddm.application.order;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.OrderUnread;
import com.apical.oddm.core.model.sys.User;


/**
 * 订单未读表操作接口
 * @author lgx
 * 2016-12-8
 */
public interface OrderUnreadServiceI extends BaseServiceI<OrderUnread> {

	/**
	 * 删除未读订单表记录
	 * @param userId
	 * @param orderId
	 */
	public void delete(int userId, int orderId);

	/**
	 * 删除 几个月前的
	 * @param month
	 */
	public void deleteMonth(int month);

	/**
	 * 批量添加订单订单未读取记录
	 * @param currUserId 当前用户id
	 * @param users用户列表
	 * @param orderId
	 */
	public void addOrderUnreadBatch(int currUserId, List<User> users,int orderId);
	
	/**
	 * 增加或则更新
	 * @param orderUnread
	 */
	public void saveOrUpdate(OrderUnread orderUnread);
}
