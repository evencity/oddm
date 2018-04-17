package com.apical.oddm.application.document;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;


/**
 * 文档资料表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface DocumentServiceI extends BaseServiceI<Document> {

	/**
	 * 获取包括订单信息
	 * @param docId
	 * @return
	 */
	public Document getDocDetail(int docId);
	
	/**
	 * 查询文档中是否已经存在路径,支持分页
	 * @param path 文档路径
	 * @return
	 */
	public List<Document> isExistPath(String path);
	
	/** 
	 * 删除所有文档相关的信息
	 */
	public void delete(int id);
	
	/**
	 * 通过物料名查询文档是否存在
	 * @param orderId
	 * @param mtName
	 * @return
	 */
	public Document isExistDocument(int orderId, String mtName);

	/**
	 * 分页获取文档列表
	 * @return 返回文档信息及订单信息
	 */
	//public Pager<Document> dataGrid();
	
	/**
	 * 通过(指定参数)分页获取文档列表
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
	 * @return 返回订单信息及文档信息
	 */
	public Pager<OrderInfo> dataOrderInfoGrid(OrderInfo orderInfo, Set<Integer> states);
	
	/**
	 * 审核页面使用，通过文档状态和所属业务分页获取文档列表
	 * @param state文档状态
	 * @param userId 用户id
	 * @return 返回文档信息及订单信息
	 */
	public Pager<Document> dataAuditByBizName(int state, int userId);
	
	/**
	 * 通过状态分页获取文档列表
	 * @param states
	 * @return 返回文档信息及订单信息
	 */
	public Pager<Document> dataGrid(Set<Integer> states);
	
	/**
	 * 通过(指定参数)分页获取文档列表
	 * @param orderInfo.setId() 订单主键
	 * @param orderInfo.setOrderNo() 订单编号
	 * @param orderInfo.setProductClient() 客户机型
 	 * @param orderInfo.setClientName() 客户名称
	 * @param orderInfo.setMerchandiser() 所属跟单
	 * @param orderInfo.setSeller() 所属业务
	 * @return 返回文档信息及订单信息
	 */
	@Deprecated
	public Pager<Document> dataGrid(OrderInfo orderInfo);
	
	/**
	 * 通过(指定参数)分页获取文档列表, 文档上传页面适用
	 * @param document.setUserId() 用户id
	 * @param document.setType() 类型
	 * @param document.getOrderInfo.setId() 订单主键
	 * @param document.getOrderInfo.setOrderNo() 订单编号
	 * @param document.getOrderInfo.setProductClient() 客户机型
 	 * @param document.getOrderInfo.setClientName() 客户名称
	 * @param document.getOrderInfo.setMerchandiser() 所属跟单
	 * @param document.getOrderInfo.setSeller() 所属业务
	 * @param states 文档状态
	 * @return 返回文档信息及订单信息
	 */
	public Pager<Document> dataGridOrderInfo(Document document,  Set<Integer> states);
	
	/**
	 * 订单下载页面适应
	 * @param document.setUnread(1) 未读，暂时不支持set其他值
	 * @param document.setOrderNo() 订单编号
	 * @param document.setUserId() 用户id
	 * @param document.setType() 类型
	 * @param document.getOrderInfo.setId() 订单主键
	 * @param document.getOrderInfo.setOrderNo() 订单编号
	 * @param document.getOrderInfo.setProductClient() 客户机型
 	 * @param document.getOrderInfo.setClientName() 客户名称
	 * @param document.getOrderInfo.setMerchandiser() 所属跟单
	 * @param document.getOrderInfo.setSeller() 所属业务
	 * @param states 文档状态
	 * @return 仅返回文档不返回订单
	 */
	public Pager<Document> dataGrid(Document document, Set<Integer> states);
	
	/**
	 * 通过上传时间分页获取文档列表
	 * @param startDate
	 * @param endDate
	 * @return 返回文档信息及订单信息
	 */
	public Pager<Document> dataGridByUploadTime(Date startDate, Date endDate);
	
	/**
	 * 更新下载次数，每次加1
	 * @param id 文档信息id
	 * @return 下载次数
	 */
	public int updateDownloadCount(int id);
	
	/**
	 * 通过订单信息表id分页获取文档列表
	 * @param orderId 订单 信息表id
	 * @return 返回文档信息及订单信息
	 */
	//public Pager<Document> dataGridByOrderId(int orderId);

	/**
	 * 通过订单号分页获取文档列表
	 * @param orderNo 订单编号
	 * @return 返回文档信息及订单信息
	 */
	//public Pager<Document> dataGridByOrderNo(String orderNo);
	
	/**
	 * 通过客户机型分页获取文档列表
	 * @param clientProduct 客户机型
	 * @return 返回文档信息及订单信息
	 */
	//public Pager<Document> dataGridByClientProduct(String clientProduct);
	
	/**
	 * 通过客户名称分页获取文档列表
	 * @param clientName 客户名称
	 * @return 返回文档信息及订单信息
	 */
	//public Pager<Document> dataGridByClientName(String clientName);
}
