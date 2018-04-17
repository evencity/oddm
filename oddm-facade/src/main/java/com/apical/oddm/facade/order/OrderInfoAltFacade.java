package com.apical.oddm.facade.order;

import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.command.OrderInfoAltCommand;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoAltDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午2:12:42 
 * @version 1.0 
 */

public interface OrderInfoAltFacade {


	/**
	 * 通过订单信息外键查询关联的所有列表
	 * @param orderId 订单信息id
	 * @return
	 */
	public BasePage<OrderInfoAltDTO> dataGrid(Integer orderId,PageCommand pageCommand);

	/**
	 * 添加定单变更记录
	 * @param 
	 * @return
	 */
	public Boolean add(OrderInfoAltCommand orderInfoAltCommand);

	/**
	 * 计算变更记录(编辑的时候)
	 * @param orderInfoBefore
	 * @param orderInfoCommand
	 * @param currUserId
	 */
	public void addEditRecord(OrderInfo orderInfoBefore,
			OrderInfoCommand orderInfoCommand, Integer currUserId);
	
	/**
	 * 计算变更记录（增加翻单的时候，创建新单不用）
	 * @param orderInfoBefore
	 * @param orderInfoCommand
	 * @param currUserId
	 */
	public void addAddRecord(OrderInfo orderInfoBefore,
			OrderInfoCommand orderInfoCommand, Integer currUserId);
	
	
	/**
	 * 增加订单未读记录
	 * @param orderNo 
	 * @param currUserId 当前用户id
	 * @param orderId 
	 * @param choose 1 审核通过调用，2 变更调用操作 
	 */
	public void addOrderUnreadInfo(String orderNo , int currUserId, int orderId, int choose);
}
