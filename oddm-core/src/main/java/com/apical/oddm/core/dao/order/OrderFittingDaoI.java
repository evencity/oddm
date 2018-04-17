package com.apical.oddm.core.dao.order;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderFitting;

/**
 * 订单配件表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderFittingDaoI extends BaseDaoI<OrderFitting> {

	/**
	 * 通过订单信息外键查询所有列表
	 * @param orderId 订单信息id
	 * @return
	 */
	public List<OrderFitting> dataGrid(int orderId);

}
