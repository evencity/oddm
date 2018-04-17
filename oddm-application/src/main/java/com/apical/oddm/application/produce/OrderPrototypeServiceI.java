package com.apical.oddm.application.produce;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderPrototype;


/**
 * 首件确认书表操作接口
 * @author lgx
 * 2016-11-1
 */
public interface OrderPrototypeServiceI extends BaseServiceI<OrderPrototype> {

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
	 * 返回首件实体
	 * @param id
	 * @return 快加载
	 */
	public OrderPrototype get(int id);
	
	/**
	 * 分页获取首件列表
	 * @param orderInfo.setId() 订单主键
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setProductClient() 客户机型
	 * @param orderInfo.setProductFactory() 工厂机型
 	 * @param orderInfo.setClientName() 客户名称
	 * @param orderInfo.setMerchandiser() 所属跟单
	 * @param orderInfo.setSeller() 所属业务
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
