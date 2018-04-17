package com.apical.oddm.facade.materialFollow;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.materialFollow.command.MaterialFollowupCommand;
import com.apical.oddm.facade.materialFollow.dto.MaterialFollowupDTO;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;


/**
 * 物料交期表操作接口
 * @author zzh
 * 2016-10-16
 */
public interface MaterialFollowupFacade {

	/**
	 * 分页获取物料交期列表
	 * @return 物料交期信息、订单基础信息
	 */
	public BasePage<MaterialFollowupDTO> dataGrid(PageCommand pageCommand);
	
	/**
	 * 通过状态分页获取物料跟进列表
	 * @param states
	 * @return 返回物料交期信息及订单信息
	 */
	public BasePage<MaterialFollowupDTO> dataGrid(Set<Integer> states);
	
	/**
	 * 通过订单信息表id分页获取物料交期列表
	 * @param orderInfo.setId() 订单id
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setUser(new User(1)) -- 用户id
	 * @return 返回物料交期信息及订单信息
	 */
	public BasePage<MaterialFollowupDTO> dataGridByOrderInfo(OrderInfoCommand orderInfoCommand,PageCommand pageCommand);

	/**
	 * 通过物料编码分页获取物料交期列表
	 * @param materialFollowup.getMtCode() 物料编码
	 * @param materialFollowup.getMerchandiser() 跟单员
	 * @return 返回物料交期信息及订单信息
	 */
	public BasePage<MaterialFollowupDTO> dataGridByMaterialFollowup(MaterialFollowupCommand materialFollowupCommand,PageCommand pageCommand);

	/**
	 * 通过交货日期分页获取物料交期列表
	 * @param startDate
	 * @param endDate
	 * @return 返回物料交期信息及订单信息
	 */
	public BasePage<MaterialFollowupDTO> dataGridByDateDeliver(Date startDate, Date endDate);
	
	/**
	 * 通过料号确定日期分页获取物料交期列表
	 * @param startDate
	 * @param endDate
	 * @return 返回物料交期信息及订单信息
	 */
	public BasePage<MaterialFollowupDTO> dataGridByDateCommit(Date startDate, Date endDate);
	
	/**
	 * 通过id获取物料交期详情
	 * @return 返回物料交期信息及订单信息
	 */
	public Integer add(MaterialFollowupCommand materialFollowupCommand);
	
	/**
	 * 通过id获取物料交期详情
	 * @return 返回物料交期信息及订单信息
	 */
	public MaterialFollowupDTO get(Integer id);
	
	/**
	 * 通过id获取物料交期详情
	 * @return 返回物料交期信息及订单信息
	 */
	public void edit(MaterialFollowupCommand materialFollowupCommand, Integer currUserI);
	
	/**
	 * 通过id获取物料交期详情
	 * @return 返回物料交期信息及订单信息
	 */
	public void delete(Integer id);
	
	/**
	 * 通过订单号或者订单信息
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id  （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @return 订单主体信息，慢加载
	 */
	public Boolean getByOrder(OrderInfoCommand orderInfoCommand);
	/**
	 * 通过订单号或者订单信息
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id  （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @return 订单主体信息，慢加载
	 */
	public OrderInfoDTO getByOrderDTO(OrderInfoCommand orderInfoCommand,Boolean hasRoles);
	
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
	 * @return 返回物料交期信息及订单信息
	 */
	public List<MaterialFollowupDTO> listAll(OrderInfoCommand orderInfoCommand, Set<Integer> states);
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
	 * @return 返回物料交期信息及订单信息
	 */
	public List<MaterialFollowupDTO> listAllByOrderId(OrderInfoCommand orderInfoCommand, Set<Integer> states);
	
	
	/**
	 */
	public Integer qualityTotal(Integer orderId);

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
	 * @return 返回物料交期信息及订单信息
	 */
	public Boolean checkExcitByOrder(OrderInfoCommand orderInfoCommand, Set<Integer> states);
	
	/**
	 * 通过订单信息参数（仅支持以下参数）分页获取订单列表，此接口适合用在订单列表页面
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setClientName() -- 客户机型
	 * @param orderInfo.setProductClient() -- 工厂机型
	 * @param orderInfo.setProductFactory() -- 工厂机型
	 * @param orderInfo.setBizName() -- 业务名称
	 * @return 订单基础信息，不含其它
	 */
	public BasePage<OrderInfoDTO> dataGridForState(OrderInfoCommand orderInfoCommand,PageCommand pageCommand, Set<Integer> states,Boolean hasRoles);
}
