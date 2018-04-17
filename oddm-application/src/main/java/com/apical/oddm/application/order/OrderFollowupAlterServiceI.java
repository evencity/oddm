package com.apical.oddm.application.order;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.OrderFollowupAlter;
import com.apical.oddm.core.model.page.Pager;


/**
 * 跟单记录表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderFollowupAlterServiceI extends BaseServiceI<OrderFollowupAlter> {

	/**
	 * 通过订单跟进表id查询关联的所有列表
	 * @param followUpId 订单跟进表id
	 * @return
	 */
	public List<OrderFollowupAlter> listGrid(int followUpId);
	
	/**
	 * 分页获取
	 * @param followUpId 订单跟进表id
	 * @return 
	 */
	public Pager<OrderFollowupAlter> dataGrid(int followUpId);
}
