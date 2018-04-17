package com.apical.oddm.application.document;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.document.DocumentDaoI;
import com.apical.oddm.core.dao.document.DocumentUnreadDaoI;
import com.apical.oddm.core.dao.order.OrderInfoDaoI;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;

@Service("documentService")
public class DocumentServiceImpl extends BaseServiceImpl<Document> implements DocumentServiceI {

	@Autowired
	private DocumentDaoI documentDao;
	
	@Autowired
	private OrderInfoDaoI orderInfoDao;
	
	@Autowired
	private DocumentUnreadDaoI documentUnreadDao;
	
/*	@Override
	public Pager<Document> dataGrid() {
		return documentDao.dataGrid();
	}*/

	@Override
	public void delete(int id) {
		documentUnreadDao.deleteAllDocId(id);
		super.delete(id);
	}

	@Override
	public Pager<Document> dataGridByUploadTime(Date startDate, Date endDate) {
		return documentDao.dataGridByUploadTime(startDate, endDate);
	}

	@Override
	public Pager<Document> dataGrid(Set<Integer> states) {
		return documentDao.dataGrid(states);
	}

	@Override
	public int updateDownloadCount(int id) {
		Document document = documentDao.get(id);
		document.setDownloadCount(document.getDownloadCount()+1);
		return document.getDownloadCount();
	}

	@Override
	public Pager<Document> dataGrid(OrderInfo orderInfo) {
		return documentDao.dataGrid(orderInfo);
	}

	@Override
	public Pager<Document> dataAuditByBizName(int state, int userId) {
		return documentDao.dataAuditByBizName(state, userId);
	}

	@Override
	public Pager<OrderInfo> dataOrderInfoGrid(OrderInfo orderInfo, Set<Integer> states) {
		return orderInfoDao.dataGrid(orderInfo, states);
	}

	@Override
	public Pager<Document> dataGridOrderInfo(Document document, Set<Integer> states) {
		return documentDao.dataGridOrderInfo(document, states);
	}

	@Override
	public List<Document> isExistPath(String path) {
		return documentDao.isExistPath(path);
	}

	@Override
	public Document isExistDocument(int orderId, String mtName) {
		return documentDao.isExistDocument(orderId, mtName);
	}

	@Override
	public Pager<Document> dataGrid(Document document, Set<Integer> states) {
		return documentDao.dataGrid(document, states);
	}

	@Override
	public Document getDocDetail(int docId) {
		return documentDao.getDocDetail(docId);
	}

	/*	@Override
	public Pager<Document> dataGridByOrderId(int orderId) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(orderId);
		return documentDao.dataGrid(orderInfo);
	}

	@Override
	public Pager<Document> dataGridByOrderNo(String orderNo) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderNo(orderNo);
		return documentDao.dataGrid(orderInfo);
	}

	@Override
	public Pager<Document> dataGridByClientProduct(String clientProduct) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setProductClient(clientProduct);
		return documentDao.dataGrid(orderInfo);
	}

	@Override
	public Pager<Document> dataGridByClientName(String clientName) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setClientName(clientName);
		return documentDao.dataGrid(orderInfo);
	}
	@Override
	public Pager<Document> dataGridByBizName(String bizName) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setBizName(bizName);
		return documentDao.dataGrid(orderInfo);
	}*/
}
