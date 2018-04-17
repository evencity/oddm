package com.apical.oddm.application.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;


/**
 * 订单跟进表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderFollowupServiceI extends BaseServiceI<OrderFollowup> {

	/**
	 * 查询明细
	 * @return 快加载
	 */
	public OrderFollowup get(int id);
		
	@Deprecated
	public void delete(int id);
	
	/**
	 * 更新更新时间
	 * @param id
	 */
	public void updateUpdateTime(int id);
	
	/**
	 * 分页获取订单跟进列表
	 * @return 订单跟进信息、订单基础信息
	 */
	//public Pager<OrderFollowup> dataGrid();
	
	/**
	 * 通过订单id查询跟单
	 * @param orderInfo.setId() 订单信息id
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @return 订单和跟单信息
	 */
	public OrderFollowup getByOrderInfo(OrderInfo orderInfo);
	
	/**
	 * 通过状态分页获取物料跟进列表
	 * @param states
	 * @return 返回订单跟进信息及订单信息
	 */
	//public Pager<OrderFollowup> dataGrid(Set<Integer> states);
	
	/**
	 * 模糊查询，通过订单信息表分页获取订单跟进列表
	 * @param orderInfo.setId() 订单信息id
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setClientName() 客户名称
	 * @param orderInfo.setClientNameCode() -- 客户编码
	 * @param orderInfo.getProductClient() 客户机型
	 * @param orderInfo.getProductFactory() 工厂机型
	 * @param orderInfo.setMerchandiser() 所属跟单
	 * @param orderInfo.setSeller() 所属业务
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id  （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @param states 跟单状态
	 * @return 返回订单跟进信息及订单信息
	 */
	public Pager<OrderFollowup> dataGridByOrderInfo(OrderInfo orderInfo, Set<Integer> states);

	/**
	 * 非模糊查询， 通过订单信息表分页获取订单跟进列表
	 * @param orderInfo.setId() 订单信息id
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setClientName() 客户名称
	 * @param orderInfo.setClientNameCode() -- 客户编码
	 * @param orderInfo.getProductClient() 客户机型
	 * @param orderInfo.getProductFactory() 工厂机型
	 * @param orderInfo.setDateOrderStart() < 大于 小于< orderInfo.setDateOrderEnd(),下单时间
	 * @param orderInfo.setMerchandiser() 所属跟单
	 * @param orderInfo.setSeller() 所属业务
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id  （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @param states 跟单状态
	 * @return 返回订单跟进信息及订单信息
	 */
	public List<OrderFollowup> listAll(OrderInfo orderInfo, Set<Integer> states);
	
	/**
	 * 通过跟单员分页获取订单跟进列表
	 * @return 返回订单跟进信息及订单信息
	 */
	//public Pager<OrderFollowup> dataGridByOrderFollowup(OrderFollowup orderFollowup);
	
	/**
	 * 通过客户交期分页获取订单跟进列表
	 * @param startDate
	 * @param endDate
	 * @return 返回订单跟进信息及订单信息
	 */
	public Pager<OrderFollowup> dataGridByDateClient(Date startDate, Date endDate);
	
	/**
	 * 通过订单下单日期分页获取订单跟进列表
	 * @param startDate
	 * @param endDate
	 * @return 返回订单跟进信息及订单信息
	 */
	public Pager<OrderFollowup> dataGridByDateOrder(Date startDate, Date endDate);
	
	
	/**
	 * 通过订单号分页获取订单跟进列表
	 * @param orderNo 订单编号
	 * @return 返回订单跟进信息及订单信息
	 */
	@Deprecated
	public Pager<OrderFollowup> dataGridByOrderNo(String orderNo);
	
	/**
	 * 通过客户名称分页获取订单跟进列表
	 * @param clientName 客户名称
	 * @return 返回订单跟进信息及订单信息
	 */
	//public Pager<OrderFollowup> dataGridByClientName(String clientName);

}
