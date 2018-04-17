package com.apical.oddm.core.dao.bom;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.core.model.page.Pager;

/**
 * BOM表单信息表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface BomInfoDaoI extends BaseDaoI<BomInfo> {

	/**
	 * 通过Bom id获取bom的全部详细信息（包括关联的所有物料）
	 * @param id
	 * @return
	 */
	public BomInfo getBomDetailById(int id);
	
	/**
	 * 更新主表更新时间，当主表、子表有变化的时候就更新
	 */
	public void updateUpdateTime(int id);
	
	/**
	 * 获取Bom表单信息
	 * @param bomInfo.setMaterialCode() 物料编码
	 * @param bomInfo.getOrderInfo().setId() 订单id
	 * @param bomInfo.getOrderInfo().setOrderNo() 订单编号
	 * @param lazy ture慢，false快
	 * @return 返回bom全部详细信息（包括关联的所有物料）快加载
	 */
	public BomInfo getBom(BomInfo bomInfo, boolean lazy);

	/**
	 * 通过订单号查找Bom
	 * @param orderNO
	 * @return
	 */
	public BomInfo getBomByOrderNo(String orderNO);
	
	/**
	 * 获取Bom表单信息
	 * @param materialCode 物料编码
	 * @return 返回bom表单
	 */
	public BomInfo getBomByMaterialCode(String materialCode);

	/**
	 * 分页获取BOM列表
	 * @return BOM信息、订单基础信息
	 */
	public Pager<BomInfo> dataGrid();
	
	/**
	 * 通过状态分页获取物料跟进列表
	 * @param states
	 * @return 返回BOM信息及订单信息
	 */
	//public Pager<BomInfo> dataGrid(Set<Integer> states);
	
	/**
	 * 通过订单信息分页获取bom列表
	 * @param orderInfo 订单信息
	 * @return 返回BOM信息及订单信息
	 */
	//public Pager<BomInfo> dataGridByOrder(OrderInfo orderInfo);
	
	/**
	 * 通过bom信息分页获bom列表
	 * @param bomInfo bom信息
	 * @return 返回BOM信息及订单信息
	 */
	public Pager<BomInfo> dataGridByBomInfo(BomInfo bomInfo, Set<Integer> states);
	
	/**
	 * 通过创建日期分页获取BOM列表
	 * @param startDate
	 * @param endDate
	 * @return 返回BOM信息及订单信息
	 */
	public Pager<BomInfo> dataGridByDateCreate(Date startDate, Date endDate);
	
	/**
	 * 通过更新日期分页获取BOM列表
	 * @param startDate
	 * @param endDate
	 * @return 返回BOM信息及订单信息
	 */
	public Pager<BomInfo> dataGridByDateUpdate(Date startDate, Date endDate);
}
