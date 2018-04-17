package com.apical.oddm.facade.manufacture;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.manufacture.command.ManufactureCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureDTO;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;

public interface ManufactureFacade {

	/**
	 * 通过参数（仅支持传以下参数）获取生产任务书列表
	 * 
	 * @param manufacture
	 *            .setOrderNo() -- 订单号
	 * @param manufacture
	 *            .setClientNameCode() -- 客户名称
	 * @return
	 */
	public BasePage<ManufactureDTO> dataGrid(ManufactureCommand manufactureCommand, PageCommand pageCommand);

	/**
	 * 通过发行日期分页获取列表
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public BasePage<ManufactureDTO> dataGridByDateIssue(Date startDate, Date endDate, PageCommand pageCommand);

	/**
	 * 通过更新日期分页获取列表
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public BasePage<ManufactureDTO> dataGridByDateUpdate(Date startDate, Date endDate, PageCommand pageCommand);

	/**
	 * 根据id立即加载对象
	 * 
	 * @param id
	 * @return 生产任务书的全部信息、快加载
	 */
	public ManufactureDTO getManufacture(Integer id,Integer currUserId);
	
	/**
	 * 可以判断生产任务书是否存在
	 * @param manufacture.setOrderNo() --  订单号
	 * @param lazy true慢 ,false快 
	 * @return 
	 */
	public ManufactureDTO getManufacture(ManufactureCommand manufactureCommand, boolean lazy,Integer currUserId);

	/**
	 * 通过生产任务书状态分页获取生产任务书列表，适合生产任务书管理-生产任务书列表
	 * 
	 * @param states
	 * @return 生产任务书基础信息，不含其它
	 */
	public BasePage<ManufactureDTO> dataGrid(Set<Integer> states);

	/**
	 * 根据id立即懒加载对象
	 * 
	 * @param id
	 * @return 生产任务书
	 */
	public ManufactureDTO getManufactureBase(Integer id);

	/**
	 * 添加
	 * 
	 * @param id
	 * @return 生产任务书
	 */
	public Boolean add(ManufactureCommand manufactureCommand);
	
	/**
	 * 添加主表
	 * 
	 * @param id
	 * @return 生产任务书
	 */
	public Integer addManufacture(ManufactureCommand manufactureCommand);

	/**
	 * 修改
	 * 
	 * @param id
	 * @return 生产任务书
	 */
	public void edit(ManufactureCommand manufactureCommand,Integer currUserId,String currUserName);

	/**
	 * 审核
	 * 
	 * @param id
	 * @return 生产任务书
	 */
	public void review(Integer id, Integer currUserId);
	/**
	 * 删除
	 * 
	 * @param id
	 * @return 生产任务书
	 */
	public void delete(Integer id);

	/**
	 * 通过订单号或者订单信息
	 * 
	 * @param orderInfo
	 *            .setOrderNo() -- 订单号
	 * @param orderInfo
	 *            .setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo
	 *            .setSellerId(1) -- 所属业务id （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @return 订单主体信息，慢加载
	 */
	public Boolean getByOrder(OrderInfoCommand orderInfoCommand);

	/**
	 * 通过订单号或者订单信息
	 * 
	 * @param orderInfo
	 *            .setOrderNo() -- 订单号
	 * @param orderInfo
	 *            .setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo
	 *            .setSellerId(1) -- 所属业务id （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @return 订单主体信息，慢加载
	 */
	public OrderInfoDTO getByOrderDTO(OrderInfoCommand orderInfoCommand);

	/**
	 * 可以判断生产任务书是否存在
	 * @param manufacture.setOrderNo() --  订单号
	 * @return 慢加载
	 */
	public ManufactureDTO getManufactureByOrderNo(String orderNo);
}
