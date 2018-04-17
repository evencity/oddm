package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePo;


/**
 * 内部订单表操作接口
 * @author lgx
 * 2016-11-1
 */
public interface SalePoServiceI extends BaseServiceI<SalePo> {
	
	/**
	 * 分页获取内部订单列表
	 * @return 返回内部订单和订单信息
	 */
	public Pager<SalePo> dataGrid();
	
	/** 
	 * 删除所有相关的信息
	 */
	public void delete(int id);
	
	/**
	 * 通过id查询内部订单明细
	 * @param id
	 * @return 返回所有关联，快加载
	 */
	public SalePo getSalePo(int id);
	
	/**
	 * 通过订单id获取SalePo信息
	 * @param orderId
	 * @param lazy true慢，false快
	 * @return 
	 */
	public SalePo getByOrderId(int orderId, boolean lazy);
	
	/**
	 * 获取最近一个单的公司基础信息，如果传入都为空，则表的最近一条
	 * @param sellerId 
	 * @param merchandiserId
	 * @return
	 */
	public SalePo getLatelyCompany(Integer sellerId, Integer merchandiserId);
	
	/**
	 * 通过(指定参数)分页获取内部订单列表
	 * @param orderInfo.setId() 订单主键
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setProductClient() 客户机型
 	 * @param orderInfo.setClientName() 客户名称
	 * @param orderInfo.setMerchandiser() 所属跟单
	 * @param orderInfo.setSeller() 所属业务
	 * @return 返回内部订单和订单信息
	 */
	/*@Deprecated
	public Pager<SalePo> dataGrid(OrderInfo orderInfo);*/
	
	/**
	 * 通过(指定参数)分页获取内部订单列表
	 * @param salePo.getOrderInfo().setId() 订单主键
	 * @param salePo.getOrderInfo().setOrderNo() 订单编号
	 * @param salePo.getOrderInfo().setProductClient() 客户机型
 	 * @param salePo.getOrderInfo().setClientName() 客户名称
	 * @param salePo.getOrderInfo().setMerchandiser() 所属跟单
	 * @param salePo.getOrderInfo().setSeller() 所属业务
	 * @param salePo.setPoNo() 项目经理
	 * @param salePo.setPm() 项目经理
	 * @param salePo.setMaker() 拟制人
	 * @param salePo.setApproverId() 批准人id
	 * @param states po状态
	 * @return 返回内部订单和订单信息
	 */
	public Pager<SalePo> dataGrid(SalePo salePo, Set<Integer> states);
	
	/**
	 * 通过交货日期分页获取内部订单列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public Pager<SalePo> dataGridByDateDelivery(Date startDate, Date endDate);
	
	/**
	 * 通过下单日期分页获取内部订单列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public Pager<SalePo> dataGridByDateOrder(Date startDate, Date endDate);
	
	/**
	 * 通过更新日期分页获取内部订单列表
	 * @param startDate
	 * @param endDate
	 * @return 返回内部订单和订单信息
	 */
	public Pager<SalePo> dataGridByDateUpdate(Date startDate, Date endDate);

	
	/**
	 * 更新内部订单更新时间，当主表、子表有变化的时候就更新主表时间
	 */
	public void updateUpdateTime(int id);
	
}
