package com.apical.oddm.core.dao.sale;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePo;

/**
 * 内部订单表dao操作接口
 * @author lgx
 * 2016-11-1
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public interface SalePoDaoI extends BaseDaoI<SalePo> {

	/**
	 * 分页获取内部订单
	 * @return 返回内部订单和订单信息
	 */
	public Pager<SalePo> dataGrid();
	
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
	 * @param orderInfo 订单信息实体对象
	 * @return 返回内部订单和订单信息
	 */
	public Pager<SalePo> dataGrid(OrderInfo orderInfo);
	
	/**
	 * 通过(指定参数)分页获取内部订单列表
	 * @param salePo
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
	 * 更新 更新时间
	 * @param id
	 */
	public void updateUpdateTime(int id);
}
