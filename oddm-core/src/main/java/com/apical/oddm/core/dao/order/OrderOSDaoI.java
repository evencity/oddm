package com.apical.oddm.core.dao.order;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderOS;

/**
 * 订单软件信息表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderOSDaoI extends BaseDaoI<OrderOS> {

	/**
	 * 通过gui获取最近一条软件信息
	 * @param gui
	 * @return
	 */
	public OrderOS getOrderOsByGui(String gui);

	/** 
	 * 通过订单信息id获取关联的软件信息
	 * @param orderId 订单信息id
	 * @return
	 */
	public OrderOS getOrderOsByOrderId(int orderId);

	/**
	 * 通过名称查询前10个，传null不查，传""默认查前10个
	 * 
	 * 一次只能传一个参数
	 * @param orderOS.setGui() -- GUI， 返回String类型
	 * @param orderOS.setLangOs() -- 系统语言，返回String类型
	 * @param orderOS.setLangDefault() 默认语言，返回String类型
	 * @param orderOS.setTimezone() 时区，返回String类型
	 * @return
	 */
	public List<Object> listTop(OrderOS orderOS);
}
