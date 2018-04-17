package com.apical.oddm.core.dao.order;

import java.util.List;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.vo.order.OrderInfoAllYearVo;
import com.apical.oddm.core.vo.order.OrderInfoYearVo;

/**
 * 订单信息表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderInfoDaoI extends BaseDaoI<OrderInfo> {

	/**
	 * 根据工厂机型获取最近一条订单信息
	 * @param productFactory
	 * @return 订单的全部信息
	 */
	public OrderInfo getByProductFactory(String productFactory);
	
	/**
	 * 更新订单更新时间，当订单表、子表有变化的时候就更新
	 */
	public void updateUpdateTime(int id);
	
	/**
	 * 当删除用户的时候，应该跟新对应订单号id为null
	 * @param userId
	 */
	public void updateUserIdNull(int userId);
	/**
	 * 分页获取订单列表
	 * @return
	 */
	//public Pager<OrderInfo> dataGrid();
	
	/**
	 * 通过订单号或者订单信息
	 * @param orderInfo
	 * @return 订单主体信息，慢加载
	 */
	public OrderInfo getByOrder(OrderInfo orderInfo);
	
	/**
	 * 通过参数获取订单列表
	 * @param orderInfo
	 * @return
	 */
	public Pager<OrderInfo> dataGrid(OrderInfo orderInfo, Set<Integer> states);
	public Pager<OrderInfo> dataGridStatistics(OrderInfo orderInfo, Set<Integer> states);

	/**
	 * 分页获取订单信息
	 * @return 返回订单信息及文档信息
	 */
	@Deprecated //用不到
	public Pager<OrderInfo> dataOrderAndDocGrid();
	
	/**
	 * 通过下单日期分页获取列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	//public Pager<OrderInfo> dataGridByDateOrder(Date startDate, Date endDate);

	/**
	 * 根据id立即加载对象，适合创建翻单、查询上一个翻单
	 * @param id
	 * @return 订单的全部信息
	 */
	public OrderInfo getOrderInfo(int id);

	/**
	 * 根据id立即加载对象，适合文档资料列表
	 * @param id
	 * @return 订单的基础信息和文档信息
	 */
	public OrderInfo getOrderInfoAndDocument(int id);
	
	/**
	 * 通过订单状态分页获取订单列表，适合订单管理-订单列表
	 * @param states
	 * @return 订单基础信息，不含其它
	 */
	//public Pager<OrderInfo> dataGrid(Set<Integer> states);

	/**
	 * 通过名称查询前10个，传null不查，传""默认查前10个
	 * @param orderInfo.setClientName() -- 客户名称
	 * @param orderInfo.setDistrict() -- 所在国家
	 * @return
	 */
	public List<Object> listTop(OrderInfo orderInfo);

	/**
	 * @param orderInfo
	 * @return
	 */
//	public Pager<OrderInfo> dataGrid(OrderInfo orderInfo);
	//分别按销售员、客户名、产品类型统计一年的订单数量
	public List<OrderInfoYearVo> getOrderInfoYearVoList(int type, String year);

	public List<OrderInfoAllYearVo> getOrderInfoAllYearVoList(String yearStart, String yearEnd);
}
