package com.apical.oddm.facade.order;

import java.util.List;
import java.util.Set;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.command.OrderFollowupCommand;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderFollowupDTO;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午2:13:06 
 * @version 1.0 
 */

public interface OrderFollowupFacade {


	/**
	 * 分页获取订单跟进列表
	 * @return 订单跟进信息、订单基础信息
	 */
	public BasePage<OrderFollowupDTO> dataGrid(OrderInfoCommand orderInfoCommand,PageCommand pageCommand);
	
	/**
	 * 通过订单id查询跟单
	 * @param orderInfo.setId() 订单信息id
	 * @param orderInfo.setOrderNo() 订单编号
	 * @return 订单和跟单信息
	 */
	public OrderFollowupDTO getByOrderInfo(OrderInfoCommand orderInfoCommand);
	
	/**
	 * 根据id查询
	 * @return 订单跟进信息、订单基础信息
	 */
	public OrderFollowupDTO get(Integer orderFollowupId);
	
	/**
	 * 通过订单号或者订单信息
	 * @param orderNo
	 * @return 订单主体信息，慢加载
	 */
	public Boolean getByOrderNo(String orderNo);
	
	/**
	 * 通过订单号或者订单信息
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id  （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @return 订单主体信息，慢加载
	 */
	public Boolean getByOrder(OrderInfoCommand orderInfoCommand);
	/**
	 * 通过订单id查询跟单
	 * @param orderInfo.setOrderNo() 订单编号
	 * @return 订单和跟单信息
	 */
	public Boolean getByOrderInfo(String orderNo);
	
	/**
	 * 通过订单id查询跟单
	 * @param orderInfo.setOrderNo() 订单编号
	 * @return 订单和跟单信息
	 */
	public OrderInfoDTO getOrderInfoByOrderNo(String orderNo);
	
	/**
	 * 添加
	 * @return 
	 */
	public Boolean add(OrderFollowupCommand orderFollowupCommand);
	
	/**
	 * 编辑
	 * @return 
	 */
	public void edit(OrderFollowupCommand orderFollowupCommand, Integer currUserId);
	
	/**
	 * 编辑
	 * @return 
	 */
	public void editForState(OrderFollowupCommand orderFollowupCommand);
	
	/**
	 * 删除
	 * @return 
	 */
	public void delete(Integer orderFollowupId);
	
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
	public List<OrderFollowupDTO> listAll(OrderInfoCommand orderInfoCommand, Set<Integer> states);
	
}
