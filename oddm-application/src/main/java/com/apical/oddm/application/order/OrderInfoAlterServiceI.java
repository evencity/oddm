package com.apical.oddm.application.order;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.OrderInfoAlt;
import com.apical.oddm.core.model.page.Pager;


/**
 * 订单变更记录表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderInfoAlterServiceI extends BaseServiceI<OrderInfoAlt> {

	/**
	 * 通过订单信息外键查询关联的所有列表
	 * @param orderId 订单信息id
	 * @return
	 */
	public List<OrderInfoAlt> listGrid(int orderId);
	
	/**
	 * 分页获取
	 * @param followUpId 订单跟进表id
	 * @return 
	 */
	public Pager<OrderInfoAlt> dataGrid(int orderId);
	
}
