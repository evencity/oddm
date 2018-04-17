package com.apical.oddm.application.produce;

import java.util.Date;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderRecord;


/**
 * 履历表操作接口
 * @author lgx
 * 2016-11-1
 */
public interface OrderRecordServiceI extends BaseServiceI<OrderRecord> {

	/**
	 * 分页获取履历列表
	 * @return 返回履历、履历类型和订单信息
	 */
	public Pager<OrderRecord> dataGrid();
	
	/**
	 * 返回履历实体
	 * @param id
	 * @return 快加载
	 */
	public OrderRecord get(int id);
	
	/**
	 * 分页获取履历列表
	 * @param orderInfo.setId() 订单主键
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setProductClient() 客户机型
	 * @param orderInfo.setProductFactory() 工厂机型
 	 * @param orderInfo.setClientName() 客户名称
	 * @param orderInfo.setMerchandiser() 所属跟单
	 * @param orderInfo.setSeller() 所属业务
	 * @return 返回履历、履历类型和订单信息
	 */
	public Pager<OrderRecord> dataGrid(OrderInfo orderInfo);
	
	/**
	 * 通过更新日期分页获取履历列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 返回履历、履历类型和订单信息
	 */
	public Pager<OrderRecord> dataGridByDateUpdate(Date startDate, Date endDate);
	
	/**
	 * 通过履历类型外键查询履历基本信息
	 * @param typeId
	 * @return 返回履历、履历类型和订单信息
	 */
	public Pager<OrderRecord> dataGridByTypeId(int typeId);
	
	/**
	 * 通过履历类型名称查询履历基本信息
	 * @param typeName
	 * @return 返回履历、履历类型和订单信息
	 */
	public Pager<OrderRecord> dataGridByTypeName(int typeName);

}
