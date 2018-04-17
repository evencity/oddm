package com.apical.oddm.core.dao.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;

/**
 * 订单跟进表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderFollowupDaoI extends BaseDaoI<OrderFollowup> {

	/**
	 * 分页获取订单跟进列表
	 * @return 订单跟进信息、订单基础信息
	 */
//	public Pager<OrderFollowup> dataGrid();
	
	/**
	 * 通过订单id查询跟单
	 * @param orderInfo
	 * @return 订单和跟单信息
	 */
	public OrderFollowup getByOrderInfo(OrderInfo orderInfo);
	
	/**
	 * 通过状态分页获取订单跟进列表
	 * @param states
	 * @return 返回订单跟进信息及订单信息
	 */
//	public Pager<OrderFollowup> dataGrid(Set<Integer> states);
	
	/**
	 * 通过订单信息分页获取订单跟进列表
	 * @param orderInfo 订单信息
	 * @return 返回订单跟进信息及订单信息
	 */
	public Pager<OrderFollowup> dataGridByOrder(OrderInfo orderInfo, Set<Integer> states);
	
	/**
	 * 通过订单信息分页获取订单跟进列表
	 * @param orderInfo 订单信息
	 * @return 返回订单跟进信息及订单信息
	 */
	public List<OrderFollowup> listAll(OrderInfo orderInfo, Set<Integer> states);
	
	/**
	 * 通过订单跟进信息分页获订单跟进列表
	 * @param orderFollowup 订单跟进信息
	 * @return 返回订单跟进信息及订单信息
	 */
	public Pager<OrderFollowup> dataGridByOrderFollowup(OrderFollowup orderFollowup);

	/**
	 * 通过客户交期分页获取订单跟进列表
	 * @param startDate
	 * @param endDate
	 * @return 返回订单跟进信息及订单信息
	 */
	public Pager<OrderFollowup> dataGridByDateClient(Date startDate, Date endDate);
	
	/**
	 * 通过订单下单日期分页获取订单跟进列表
	 * @param startDate
	 * @param endDate
	 * @return 返回订单跟进信息及订单信息
	 */
	public Pager<OrderFollowup> dataGridByDateOrder(Date startDate, Date endDate);

	public void updateUpdateTime(int id);
}
