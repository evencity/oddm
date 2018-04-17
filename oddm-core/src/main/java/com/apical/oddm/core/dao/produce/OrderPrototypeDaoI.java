package com.apical.oddm.core.dao.produce;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderPrototype;

/**
 * 首件确认书主表表dao操作接口
 * @author lgx
 * 2016-11-1
 */
public interface OrderPrototypeDaoI extends BaseDaoI<OrderPrototype> {

	/**
	 * 分页获取首件列表
	 * @return 返回首件和订单信息
	 */
	public Pager<OrderPrototype> dataGrid();
	
	/**
	 * 通过状态分页获取首件列表
	 * @param states
	 * @return 返回首件和订单信息
	 */
	public Pager<OrderPrototype> dataGrid(Set<Integer> states);
	
	/**
	 * 分页获取首件列表
	 * @param orderInfo
	 * @return 返回首件和订单信息
	 */
	public Pager<OrderPrototype> dataGrid(OrderInfo orderInfo);
	
	/**
	 * 通过更新日期分页获取首件列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 返回首件和订单信息
	 */
	public Pager<OrderPrototype> dataGridByDateUpdate(Date startDate, Date endDate);
}
