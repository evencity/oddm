package com.apical.oddm.facade.order;

import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.command.OrderFollowupAlterCommand;
import com.apical.oddm.facade.order.command.OrderFollowupCommand;
import com.apical.oddm.facade.order.dto.OrderFollowupAlterDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午2:12:42 
 * @version 1.0 
 */

public interface OrderFollowupAlterFacade {

	/**
	 * 通过订单跟进表id查询关联的所有列表
	 * @param followUpId 订单跟进表id
	 * @return
	 */
	public BasePage<OrderFollowupAlterDTO> dataGrid(Integer followUpId,PageCommand pageCommand);
	
	/**
	 * 添加纪录
	 * @param 
	 * @return
	 */
	public Boolean add(OrderFollowupAlterCommand orderFollowupAlterCommand);

	/**
	 * 计算变更记录
	 * @param orderFollowupbefore
	 * @param orderFollowupCommand
	 * @param currUserId
	 */
	void addEditRecord(OrderFollowup orderFollowupbefore,
			OrderFollowupCommand orderFollowupCommand, Integer currUserId);
}
