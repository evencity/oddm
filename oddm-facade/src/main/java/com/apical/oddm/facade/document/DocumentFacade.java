package com.apical.oddm.facade.document;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.command.DocumentCommand;
import com.apical.oddm.facade.document.dto.DocumentDTO;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午4:14:02 
 * @version 1.0 
 */

public interface DocumentFacade {

	/**
	 * 审核页面使用，通过文档状态和所属业务分页获取文档列表
	 * @param state文档状态
	 * @param bizName 用户id
	 * @return 返回文档信息及订单信息
	 */
	public BasePage<DocumentDTO> dataAuditByBizName(Integer state, Integer userId,PageCommand pageCommand);
	
	/**
	 * 分页获取订单信息
	 * @return 返回订单信息及文档信息
	 */
	public BasePage<OrderInfoDTO> dataOrderInfoGrid(OrderInfoCommand orderInfoCommand,Set<Integer> set,PageCommand pageCommand,Boolean hasRoles);

	/**
	 * 通过状态分页获取文档列表
	 * @param states
	 * @return 返回文档信息及订单信息
	 */
	public BasePage<DocumentDTO> dataGrid(Set<Integer> states,PageCommand pageCommand);
	
	/**
	 * 通过(指定参数)分页获取文档列表
	 * @param orderInfo.setId() 订单主键
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setProductClient() 客户机型
 	 * @param orderInfo.setClientName() 客户名称
	 * @param orderInfo.setBizName() 所属业务
	 * @return 返回文档信息及订单信息
	 */
	public BasePage<DocumentDTO> dataGrid(OrderInfoCommand orderInfoCommand,PageCommand pageCommand);
	
	/**
	 * 通过(指定参数)分页获取文档列表
	 * @param document.setType() 类型
	 * @param document.getOrderInfo.setId() 订单主键
	 * @param document.getOrderInfo.setOrderNo() 订单编号
	 * @param document.getOrderInfo.setProductClient() 客户机型
 	 * @param document.getOrderInfo.setClientName() 客户名称
	 * @param document.getOrderInfo.setMerchandiser() 所属跟单
	 * @param document.getOrderInfo.setSeller() 所属业务
	 * @param states 状态
	 * @return 返回文档信息及订单信息
	 */
	public BasePage<DocumentDTO> dataGridOrderInfo(DocumentCommand documentCommand, Set<Integer> states,PageCommand pageCommand);
	
	/**
	 * 通过上传时间分页获取文档列表
	 * @param startDate
	 * @param endDate
	 * @return 返回文档信息及订单信息
	 */
	public BasePage<DocumentDTO> dataGridByUploadTime(Date startDate, Date endDate,PageCommand pageCommand);
	
	
	/**
	 * 根据Id删除文档信息
	 * @return 
	 */
	public void delete(Integer documentId);
	
	/**
	 * 审核文档 3.通过  4.不通过
	 * @return 
	 */
	public void reviewed(Integer id,Integer pass,Integer orderId, Integer currUserId);
	
	/**
	 * 添加文档信息
	 * @return 
	 */
	public Boolean add(DocumentCommand documentCommand);
	
	/**
	 * 下载使用更新次数
	 * @return 
	 */
	public void editDocCount(DocumentCommand documentCommand);
	
	/**
	 * 根据Id获取文档信息
	 * @return 
	 */
	public void editDocPath(DocumentCommand documentCommand);
	
	/**
	 * 根据Id获取文档信息
	 * @return 
	 */
	public DocumentDTO get(Integer documentId);
	/**
	 * 根据Id获取文档信息
	 * @return 
	 */
	public void uploadFile(DocumentCommand documentCommand);
	
	/**
	 * 查询文档中是否已经存在路径,支持分页
	 * @param path 文档路径
	 * @return
	 */
	public List<DocumentDTO> isExistPath(String path);
	
	/**
	 * 通过物料名查询文档是否存在
	 * @param orderId
	 * @param mtName
	 * @return
	 */
	public Boolean isExistDocument(Integer orderId, String mtName);
	
	
	/**
	  * 订单下载页面适应
	 * @param document.setUnread(1) 未读，暂时不支持set其他值
	 * @param document.setOrderNo() 订单编号
	 * @param document.setUserId() 用户id
	 * @param states 文档状态
	 * @return 仅返回文档不返回订单
	 */
	public BasePage<DocumentDTO> dataGrid(DocumentCommand documentCommand, Set<Integer> states,PageCommand pageCommand);
	
	
}
