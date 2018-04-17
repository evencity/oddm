package com.apical.oddm.application.order;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.OrderFitting;


/**
 * 订单配件表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderFittingServiceI extends BaseServiceI<OrderFitting> {

	/**
	 * 通过订单信息外键查询关联的所有列表
	 * @param orderId 订单信息id
	 * @return
	 */
	public List<OrderFitting> dataGrid(int orderId);
}
