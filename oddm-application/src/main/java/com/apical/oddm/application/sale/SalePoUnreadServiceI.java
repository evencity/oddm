package com.apical.oddm.application.sale;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.sale.SalePoUnread;
import com.apical.oddm.core.model.sys.User;


/**
 * 内部订单未读表操作接口
 * @author lgx
 * 2016-12-8
 */
public interface SalePoUnreadServiceI extends BaseServiceI<SalePoUnread> {

	/**
	 * 删除未读内部订单表记录
	 * @param userId
	 * @param poId
	 */
	public void delete(int userId, int poId);

	/**
	 * 删除 几个月前的
	 * @param month
	 */
	public void deleteMonth(int month);

	/**
	 * 批量添加内部订单内部订单未读取记录
	 * @param currUserId 当前用户id
	 * @param users用户列表
	 * @param poId
	 */
	public void addSalePoUnreadBatch(int currUserId, List<User> users,int poId);
	
	/**
	 * 增加或则更新
	 * @param orderUnread
	 */
	public void saveOrUpdate(SalePoUnread orderUnread);
}
