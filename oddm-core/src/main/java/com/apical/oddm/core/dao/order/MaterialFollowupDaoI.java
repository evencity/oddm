package com.apical.oddm.core.dao.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;

/**
 * 物料交期表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface MaterialFollowupDaoI extends BaseDaoI<MaterialFollowup> {

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
	public Pager<MaterialFollowup> dataGrid(Set<Integer> states);
	
	/**
	 * 通过订单信息分页获取物料交期列表
	 * @param orderInfo 订单信息
	 * @return 返回物料交期信息及订单信息
	 */
	public Pager<MaterialFollowup> dataGridByOrder(OrderInfo orderInfo, Set<Integer> states);
	
	/**
	 * 通过订单信息分页获取物料交期列表
	 * @param orderInfo 订单信息
	 * @param lazy 
	 * @return 返回物料交期信息及订单信息
	 */
	public List<MaterialFollowup> listAll(OrderInfo orderInfo, Set<Integer> states, boolean lazy);
	/**
	 * 通过物料跟进信息分页获取物料交期列表
	 * @param materialFollowup 物料跟进信息
	 * @return 返回物料交期信息及订单信息
	 */
	//public Pager<MaterialFollowup> dataGridByMaterialFollowup(MaterialFollowup materialFollowup);

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
