package com.apical.oddm.facade.bom;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.bom.dto.BomDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialDTO;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;

/**
 * BOM表单信息表操作接口
 * @author zzh
 */
public interface BomFacade {

	/**
	 * 通过Bom id获取bom的全部详细信息（包括关联的所有物料）
	 * @param id
	 * @return
	 */
	public BomDTO getBomDetailById(Integer id,Integer currUserId);
	
	/**
	 * 更新主表更新时间，当主表、子表有变化的时候就更新
	 */
	public void updateUpdateTime(Integer id);
	
	/**
	 * 获取Bom表单信息
	 * @param orderid 订单id
	 * @return 返回bom全部详细信息（包括关联的所有物料）
	 */
	public BomDTO getBomByOrderId(Integer orderId,Integer currUserId);

	/**
	 * 获取Bom表单信息
	 * @param materialCode 物料编码
	 * @return 返回bom表单,慢加载
	 */
	public BomDTO getBomByMaterialCode(String materialCode);

	/**
	 * 通过订单号查找Bom
	 * @param orderNO
	 * @return 返回bom表单及订单信息
	 */
	public BomDTO getBomByOrderNo(String orderNO);

	/**
	 * 分页获取BOM列表
	 * @return BOM信息、订单基础信息
	 */
	//public BasePage<BomDTO> dataGrid();
	
	/**
	 * 通过状态分页获取BOM列表
	 * @param states
	 * @return 返回BOM信息及订单信息
	 */
	//public BasePage<BomDTO> dataGrid(Set<Integer> states,PageCommand pageCommand);
	
	/**
	 * 通过订单信息表id分页获取BOM列表
	 * @param orderId 订单信息表id
	 * @return 返回BOM信息及订单信息
	 */
	//public BasePage<BomDTO> dataGridByOrderId(Integer orderId,PageCommand pageCommand);
	
	/**
	 * 传入指定的参数(支持下面参数)分页获取BOM列表
	 * @param bomInfo.setMaker() -- 制表人
	 * @param bomInfo.setMaterialCode() -- 物料编码
	 * @param bomInfo.setProductName() -- 品名
	 * @return 返回BOM信息及订单信息
	 */
//	public  BasePage<BomDTO> dataGridByBomInfo(BomDTO bomDTO,PageCommand pageCommand);
	
	/**
	 * 传入指定的参数(支持下面参数)分页获取BOM列表
	 * @param orderInfo.setId() 订单id
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setClientName() 客户名称
	 * @return 返回BOM信息及订单信息
	 */
//	public  BasePage<BomDTO> dataGridByOrderInfo(OrderInfoCommand orderInfoCommand,PageCommand pageCommand);
	
	/**
	 * 传入指定的参数(支持下面参数)分页获取BOM列表
	 * @param bomInfo.setMaker() -- 制表人
	 * @param bomInfo.setMaterialCode() -- 物料编码
	 * @param bomInfo.setProductName() -- 品名
	 * @param orderInfo.setOrderNo() 订单编号
	 * @return 返回BOM信息及订单信息
	 */
	public  BasePage<BomDTO> dataGrid(BomDTO bomDTO,Set<Integer> states,PageCommand pageCommand);
	/**
	 * 通过创建日期分页获取BOM列表
	 * @param startDate
	 * @param endDate
	 * @return 返回BOM信息及订单信息
	 */
	public BasePage<BomDTO> dataGridByDateCreate(Date startDate, Date endDate,PageCommand pageCommand);
	
	/**
	 * 添加
	 */
	public Integer add(BomDTO bomDTO);
	
	/** 
	 * 删除所有bom相关的信息
	 */
	public void delete(Integer id);
	
	/** 
	 * 编辑
	 */
	public void edit(BomDTO bomDTO,Integer currUserId,String currUserName);
	

	/**
	 * 通过订单号或者订单信息
	 * 
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @return 订单主体信息，慢加载
	 */
	public Boolean getByOrder(OrderInfoCommand orderInfoCommand);
	
	/**
	 * 通过订单号或者订单信息
	 * 
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @return 订单主体信息，慢加载
	 */
	public OrderInfoDTO getByOrderDTO(OrderInfoCommand orderInfoCommand);

	/**
	 * 审核
	 * @param id bom的id
	 * @param currId 当前用户id
	 */
	public void review(Integer id, Integer currId,String orderNo);
	
	/**
	 * 根据物料编码
	 * @param materialCode
	 * @return
	 */
	public BomMaterialDTO getByMaterialCode(String materialCode);
	
}
