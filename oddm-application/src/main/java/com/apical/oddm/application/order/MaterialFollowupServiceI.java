package com.apical.oddm.application.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;


/**
 * 物料交期表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface MaterialFollowupServiceI extends BaseServiceI<MaterialFollowup> {

	/**
	 * 通过订单id查询物料交期
	 * @param orderInfo.setId() 订单信息id
	 * @param orderInfo.setOrderNo() 订单编号
	 * @return 订单和物料交期信息
	 */
	//public List<MaterialFollowup> getByOrderInfo(OrderInfo orderInfo);
	
	/**
	 * 通多物料交期id查看订单和物料交期明细
	 * @param id
	 * @return 订单和物料交期明细
	 */
	public MaterialFollowup getMaterialFollowup(int id);
	
	/**
	 * 分页获取物料交期列表
	 * @return 物料交期信息、订单基础信息
	 */
	public Pager<MaterialFollowup> dataGrid();
	
	/**
	 * 通过状态分页获取物料跟进列表
	 * @param states
	 * @return 返回物料交期信息及订单信息
	 */
	@Deprecated
	public Pager<MaterialFollowup> dataGrid(Set<Integer> states);
	
	/**
	 * 模糊查询， 通过订单信息表id分页获取物料交期列表
	 * @param orderInfo.setId() 订单id
	 * @param orderInfo.setClientName() 客户名称
	 * @param orderInfo.setClientNameCode() -- 客户编码
	 * @param orderInfo.getProductClient() 客户机型
	 * @param orderInfo.getProductFactory() 工厂机型
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setMerchandiser() 所属跟单
	 * @param orderInfo.setSeller() 所属业务
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id  （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @param states 物料交期状态
	 * @return 返回物料交期信息及订单信息
	 */
	public Pager<MaterialFollowup> dataGridByOrderInfo(OrderInfo orderInfo, Set<Integer> states);
	
	/**
	 * 非模糊查询， 通过订单信息表id分页获取物料交期列表
	 * @param orderInfo.setId() 订单id
	 * @param orderInfo.setClientName() 客户名称
	 * @param orderInfo.setClientNameCode() -- 客户编码
	 * @param orderInfo.getProductClient() 客户机型
	 * @param orderInfo.getProductFactory() 工厂机型
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setDateOrderStart() < 大于 小于< orderInfo.setDateOrderEnd(),下单时间
	 * @param orderInfo.setMerchandiser() 所属跟单
	 * @param orderInfo.setSeller() 所属业务
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id  （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @param states 物料交期状态
	 * @param lazy true为慢，false为快
	 * @return 返回物料交期信息及订单信息
	 */
	public List<MaterialFollowup> listAll(OrderInfo orderInfo, Set<Integer> states, boolean lazy);

	/**
	 * 通过订单号分页获取物料交期列表
	 * @param orderNo 订单编号
	 * @return 返回物料交期信息及订单信息
	 */
//	public Pager<MaterialFollowup> dataGridByOrderNo(String orderNo);
	
	/**
	 * 通过业务名称分页获取物料交期列表
	 * @param bizName 业务名称
	 * @return 返回物料交期信息及订单信息
	 */
//	public Pager<MaterialFollowup> dataGridByUserId(int userId);

	/**
	 * 通过物料编码分页获取物料交期列表
	 * @param materialFollowup.getMtCode() 物料编码
	 * @param materialFollowup.getMerchandiser() 跟单员
	 * @return 返回物料交期信息及订单信息
	 */
	//public Pager<MaterialFollowup> dataGridByMaterialFollowup(MaterialFollowup materialFollowup);

	/**
	 * 通过跟单员分页获取物料交期列表
	 * @param merchandiser 跟单员
	 * @return 返回物料交期信息及订单信息
	 */
	//public Pager<MaterialFollowup> dataGridByMerchandiser(String merchandiser);
	
	/**
	 * 通过交货日期分页获取物料交期列表
	 * @param startDate
	 * @param endDate
	 * @return 返回物料交期信息及订单信息
	 */
	public Pager<MaterialFollowup> dataGridByDateDeliver(Date startDate, Date endDate);
	
	/**
	 * 通过料号确定日期分页获取物料交期列表
	 * @param startDate
	 * @param endDate
	 * @return 返回物料交期信息及订单信息
	 */
	public Pager<MaterialFollowup> dataGridByDateCommit(Date startDate, Date endDate);
}
