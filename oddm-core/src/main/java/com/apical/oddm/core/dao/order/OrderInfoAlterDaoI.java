package com.apical.oddm.core.dao.order;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderInfoAlt;
import com.apical.oddm.core.model.page.Pager;

/**
 * 订单信息更改记录表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderInfoAlterDaoI extends BaseDaoI<OrderInfoAlt> {

	/**
	 * 通过订单信息外键查询关联的所有列表
	 * @param orderId 订单信息id
	 * @return
	 */
	public List<OrderInfoAlt> listGrid(int orderId);
	
	/**
	 * 分页获取
	 * @return 
	 */
	public Pager<OrderInfoAlt> dataGrid(int orderId);
}
