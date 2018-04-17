package com.apical.oddm.core.dao.order;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderShell;

/**
 * 订单外壳工艺表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderShellDaoI extends BaseDaoI<OrderShell> {

	/**
	 * 通过订单信息外键查询所有列表
	 * @param orderId 订单信息id
	 * @return
	 */
	public List<OrderShell> dataGrid(int orderId);

	
}
