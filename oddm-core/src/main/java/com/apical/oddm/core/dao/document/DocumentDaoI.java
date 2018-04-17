package com.apical.oddm.core.dao.document;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;

/**
 * 文档信息表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface DocumentDaoI extends BaseDaoI<Document> {

	/**
	 * 获取包括订单信息
	 * @param docId
	 * @return
	 */
	public Document getDocDetail(int docId);
	
	/**
	 * 分页获取文档列表
	 * @return 返回文档信息及订单信息
	 */
	public Pager<Document> dataGrid();

	/**
	 * 通过物料名查询文档是否存在
	 * @param orderId
	 * @param mtName
	 * @return
	 */
	public Document isExistDocument(int orderId, String mtName);
	
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
	 * 分页获取文档列表
	 * @param orderInfo
	 * @return 返回文档信息及订单信息
	 */
	public Pager<Document> dataGrid(OrderInfo orderInfo);
	
	/**
	 * 通过(指定参数)分页获取文档列表
	 * @param orderInfo 订单信息
	 * @param states 文档状态
	 * @return 返回文档信息及订单信息
	 */
	public Pager<Document> dataGridOrderInfo(Document document, Set<Integer> states);
	
	/**
	 * 订单下载页面适应
	 * @param document.setUnread(1) 未读，暂时不支持set其他值
	 * @param document.setOrderNo() 订单编号
	 * @param states 文档状态
	 * @return
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
	 * 查询文档中是否已经存在路径
	 * @param path 文档路径
	 * @return
	 */
	public List<Document> isExistPath(String path);
}
