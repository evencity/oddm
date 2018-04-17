package com.apical.oddm.core.dao.order;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderFollowupAlter;
import com.apical.oddm.core.model.page.Pager;

/**
 * 订单跟进修改记录表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderFollowupAlterDaoI extends BaseDaoI<OrderFollowupAlter> {

	/**
	 * 通过订单跟进表id查询关联的所有列表
	 * @param followUpId 订单跟进表id
	 * @return
	 */
	public List<OrderFollowupAlter> listGrid(int followUpId);
	
	/**
	 * 分页获取
	 * @return 
	 */
	public Pager<OrderFollowupAlter> dataGrid(int followUpId);
}
