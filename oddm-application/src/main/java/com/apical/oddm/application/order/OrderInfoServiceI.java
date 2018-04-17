package com.apical.oddm.application.order;

import java.util.List;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.vo.order.OrderInfoAllYearVo;
import com.apical.oddm.core.vo.order.OrderInfoYearVo;


/**
 * 订单信息表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface OrderInfoServiceI extends BaseServiceI<OrderInfo> {
	
/*	public Serializable add(OrderInfo t);
	public void edit(OrderInfo t);//使用merge(t)
*/
	/**
	 * 根据id立即加载对象，适合查询订单全部信息、创建翻单、查询上一个翻单
	 * 
	 * SystemContext.setCurrUserId(userId); 需要传入当前登录用户id，方便置订单为已读状态。
	 * @param id
	 * @return 订单的全部信息
	 */
	public OrderInfo getOrderInfo(int id);
	
	/**
	 * 更新订单更新时间，当订单表、子表有变化的时候就更新
	 */
	public void updateUpdateTime(int id);
	
	/**
	 * 根据工厂机型获取最近一条订单信息
	 * @param productFactory
	 * @return 订单的全部信息
	 */
	public OrderInfo getByProductFactory(String productFactory);
	
	/**
	 * 通过订单号或者订单信息
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id  （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @return 订单主体信息，慢加载
	 */
	public OrderInfo getByOrder(OrderInfo orderInfo);
	
	/**
	 * 通过订单号查询订单，建议使用getByOrder(OrderInfo orderInfo)
	 * @param orderNo
	 * @return
	 */
	@Deprecated()
	public OrderInfo getByOrderNo(String orderNo);

	/**
	 * 根据id删除对象，会删除和订单关联的基本，不包括：文档、bom、订单跟进、物料跟进等
	 * @param id
	 * @throws Exception 如果文档、bom、订单跟进、物料跟进等有数据，则抛出异常，不给删除
	 */
	@Deprecated
	public void delete(int id);
	
	/**
	 * 根据id删除对象，删除对象包括有：bom（包括变更记录）、物料跟进（包括变更记录）、订单跟进（包括变更记录）、文档、
	 * 订单内容（软件信息、外壳、硬件、包材、变更记录等）、生产任务书等所有与订单相关的记录。
	 * 注意：慎用，只有超级管理员权限的人才能用。
	 * @param id
	 */
	@Deprecated
	public void deleteAll(int id);
	
	/**
	 * 分页获取订单列表，适合数据维护-订单管理菜单(具有全部权限)
	 * @return 订单基础信息，不含其它
	 */
	//public Pager<OrderInfo> dataGrid();
	
	/**
	 * 通过订单信息表id分页获取文档列表（包括历史翻单资料）
	 * @param orderId 订单 信息表id
	 * @return 返回文档信息及订单信息、及历史翻单的所有资料
	 */
	public List<OrderInfo> getAllDocumentByOrderId(int orderId);
	
	/**
	 * 通过订单信息参数（仅支持以下参数）分页获取订单列表，此接口适合用在订单列表页面
	 * 
	 * SystemContext.setCurrUserId(userId); 需要传入当前登录用id，查询订单的读取状态
	 * @param orderInfo.setUnread() -- 查询订单未读，要和SystemContext.setCurrUserId(userId)一起用
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setClientName() -- 客户名称
	 * @param orderInfo.setClientNameCode() -- 客户编码
	 * @param orderInfo.setProductClient() -- 客户机型
	 * @param orderInfo.setProductFactory() -- 工厂机型
	 * @param orderInfo.setMerchandiser() 所属跟单
	 * @param orderInfo.setSeller() 所属业务
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id  （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @param states 订单状态
	 * @return 订单基础信息，不含其它
	 */
	public Pager<OrderInfo> dataGrid(OrderInfo orderInfo, Set<Integer> states);

	/**
	 * 订单汇总页面适应，此接口绑定表多，效率较上面的那个慢
	 * @param orderInfo
	 * @param states
	 * @return
	 */
	public Pager<OrderInfo> dataGridStatistics(OrderInfo orderInfo, Set<Integer> states);

	/**
	 * 触发生成 bom、订单跟进、物料交期、生产任务书
	 * @param orderId
	 * @return
	 */
	public boolean addOtherEntity(int orderId);
	
	/**
	 * 通过名称查询前10个，传null不查，传""默认查前10个
	 * 
	 * 一次只能传一个参数
	 * @param orderInfo.setClientName() -- 客户名称，返回String类型
	 * @param orderInfo.setClientBrand() -- 客户铭牌，返回String类型
	 * @param orderInfo.setDistrict() -- 所在国家，返回String类型
	 * @return
	 */
	public List<Object> listTop(OrderInfo orderInfo);
	
	//分别按销售员、客户名、产品类型统计一年的订单数量
	public List<OrderInfoYearVo> getOrderInfoYearVoList(int type, String year);
	
	/**
	 * 根据年，返回年的每个月与订单数量之间的对应关系
	 * @param yearStart 2013
	 * @param yearEnd 2017
	 * @return
	 */
	public List<OrderInfoAllYearVo> getOrderInfoAllYearVoList(String yearStart, String yearEnd);
}
