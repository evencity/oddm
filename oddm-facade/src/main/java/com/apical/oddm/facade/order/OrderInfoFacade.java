package com.apical.oddm.facade.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.apical.oddm.core.vo.order.OrderInfoAllYearVo;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.order.dto.OrderInfoYearDto;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月31日 上午11:37:10 
 * @version 1.0 
 */

public interface OrderInfoFacade {

	/**
	 * 根据id立即加载对象，适合查询订单全部信息、创建翻单、查询上一个翻单
	 * @param id
	 * @return 订单的全部信息
	 */
	public OrderInfoDTO getOrderInfoDTO(int id,Boolean hasRoles,Integer currUserId);
	

	/**
	 * 根据id懒加载
	 * @param id
	 * @return 订单的全部信息
	 */
	public OrderInfoDTO getBaseOrderInfoDTO(int id,Boolean hasRoles);
	
	/**
	 * 根据工厂机型获取最近一条订单信息
	 * @param productFactory
	 * @return 订单的全部信息
	 */
	public OrderInfoDTO getByProductFactory(String productFactory);
	
	/**
	 * 通过订单号或者订单信息
	 * @param orderNo
	 */
	public Boolean getByOrderNo(String orderNo);
	
	/**
	 * 通过订单信息表id分页获取文档列表（包括历史翻单资料）
	 * @param orderId 订单 信息表id
	 * @return 返回文档信息及订单信息、及历史翻单的所有资料
	 */
	public List<OrderInfoDTO> getAllDocumentByOrderId(Integer orderId);
	
	/**
	 * 通过订单状态分页获取订单列表，适合订单管理-订单列表
	 * @param states
	 * @return 订单基础信息，不含其它
	 */
	public BasePage<OrderInfoDTO> dataGrid(Set<Integer> states);
	
	/**
	 * 通过订单信息参数（仅支持以下参数）分页获取订单列表，此接口适合用在订单列表页面
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setClientName() -- 客户机型
	 * @param orderInfo.setProductClient() -- 工厂机型
	 * @param orderInfo.setProductFactory() -- 工厂机型
	 * @param orderInfo.setBizName() -- 业务名称
	 * @return 订单基础信息，不含其它
	 */
	public BasePage<OrderInfoDTO> dataGrid(OrderInfoCommand orderInfoCommand,PageCommand pageCommand, Set<Integer> states,Boolean hasRoles);
	
	public BasePage<OrderInfoDTO> dataGridStatistics(OrderInfoCommand orderInfoCommand,PageCommand pageCommand, Set<Integer> states);

	/**
	 * 通过下单日期分页获取列表，此接口适合用在订单列表页面
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 订单基础信息，不含其它
	 */
	public BasePage<OrderInfoDTO> dataGrid(Date startDate, Date endDate,PageCommand pageCommand);
	
	/**
	 * 创建新定单
	 */
	public Boolean add(OrderInfoCommand orderInfoCommand);
	
	/**
	 * 创建翻单用到，默认物料为旧
	 */
	public Boolean addNewOrder(OrderInfoCommand orderInfoCommand, Integer currUserId);
	/**
	 * 通过名称查询前10个，传null不查，传""默认查前10个以id降序排列。
	 * 
	 * 一次只能传一个参数
	 * @param orderInfo.setClientName() -- 客户名称，返回String类型
	 * @return
	 */
	public List<String> listTopClientName(String clientName);
	public List<String> listTopClientBrand(String clientBrand);
	public List<String> listTopDistrict(String district);
	
	/**
	 * 通过名称查询前10个，传null不查，传""默认查前10个以id降序排列。
	 * 
	 * 一次只能传一个参数
	 * @param orderOS.setGui() -- GUI， 返回String类型
	 * @param orderOS.setLangOs() -- 系统语言，返回String类型
	 * @param orderOS.setLangDefault() 默认语言，返回String类型
	 * @param orderOS.setTimezone() 时区，返回String类型
	 * @return
	 */
	public List<String> listTopGui(String gui);
	public List<String> listTopLangOs(String langOs);
	public List<String> listTopLangDefault(String langDefault);
	public List<String> listTopTimezone(String timezone);
	/**
	 * 编辑定单
	 */
	public void edit(OrderInfoCommand orderInfoCommand, Integer currUserId);
	
	/**
	 * 添加业务
	 */
	public Boolean addSeller(Integer orderId,Integer sellerId,String seller);
	
	/**
	 * 业务审核操作
	 * @param currUserId 
	 */
	public Boolean orderReview(Integer orderId,Integer state, Integer currUserId);
	
	public OrderInfoDTO getOrderInfoByNo(String OrderNo);
	
	/**
	 * 修改跟单
	 */
	public void changeMerchandiser(OrderInfoCommand orderInfoCommand);
	

	/**
	 * 修改业务
	 */
	public void changeSeller(OrderInfoCommand orderInfoCommand);

	/**
	 * 删除订单
	 */
	public void delete(Integer id);
	
	/**
	 * 统计业务接单明细 （一年）
	 * @param year
	 * @return
	 */
	public List<OrderInfoYearDto> getOrderInfoYearSellerVoList(int type, String year);


	/**
	 * 根据年，返回年的每个月与订单数量之间的对应关系
	 * @param yearStart 2013
	 * @param yearEnd 2017
	 * @return
	 */
	public List<OrderInfoAllYearVo> getOrderInfoAllYearVoList(String yearStart,
			String yearEnd);
}

