package com.apical.oddm.core.dao.document;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;

@Repository("documentDao")
public class DocumentDaoImpl extends BaseDaoImpl<Document> implements DocumentDaoI {

	@Override
	public Pager<Document> dataGrid() {
		String hql = "select t from Document t join fetch t.orderInfo o order by t.uploadtime desc";
		return this.find(hql);
	}

	@Override
	public Pager<Document> dataGrid(OrderInfo orderInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from Document t join fetch t.orderInfo o where 1=1";
		if (orderInfo != null) {
			if (orderInfo.getId() != null) {
				hql +=" and o.id=:orderId";
				params.put("orderId", orderInfo.getId());
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
				hql +=" and o.orderNo like :orderNo";
				params.put("orderNo", "%"+orderInfo.getOrderNo()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
				hql +=" and o.productClient like :productClient";
				params.put("productClient", "%"+orderInfo.getProductClient()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getClientName())) {
				hql +=" and o.clientName like :clientName";
				params.put("clientName", "%"+orderInfo.getClientName()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getSeller())) {
				hql +=" and o.seller like :seller";
				params.put("seller", "%"+orderInfo.getSeller()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getMerchandiser())) {
				hql +=" and o.merchandiser like :merchandiser";
				params.put("merchandiser", "%"+orderInfo.getMerchandiser()+"%");
			}
			/*if (orderInfo.getUser() != null) {
				if (orderInfo.getUser().getUsername() != null) {
					hql +=" and o.user.username=:username";
					params.put("username", orderInfo.getUser().getUsername());
				}
			}*/
		}
		hql += " order by t.uploadtime desc";
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<Document> dataGridByUploadTime(Date startDate, Date endDate) {
		String hql = "select t from Document t join fetch t.orderInfo o where t.uploadtime>=:startDate and t.uploadtime<=:endDate order by t.uploadtime desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<Document> dataGrid(Set<Integer> states) {
		return this.find("select t from Document t join fetch t.orderInfo o where t.state in ("
				+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ") order by t.uploadtime desc");
	}

	@Override
	public Pager<Document> dataAuditByBizName(int state, int userId) {
		String hql = "select t from Document t join fetch t.orderInfo o where t.state=:state and (o.sellerId=:userId or o.merchandiserId=:userId) order by t.uploadtime desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("state", state);
		params.put("userId", userId);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<Document> dataGridOrderInfo(Document document, Set<Integer> states) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from Document t join fetch t.orderInfo o where 1=1";
		if (states != null) {
			if (!states.isEmpty()) {
				hql +=" and t.state in ("+org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
			}
		}
		if (document != null) {
			if (document.getUserId() != null) {
				hql +=" and t.userId=:userId";
				params.put("userId", document.getUserId());
			}
			if (document.getType() != null) {
				hql +=" and t.type=:type";
				params.put("type", document.getType());
			}
			OrderInfo orderInfo = document.getOrderInfo();
			if (orderInfo != null) {
				if (orderInfo.getId() != null) {
					hql +=" and o.id=:orderId";
					params.put("orderId", orderInfo.getId());
				}
				if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
					hql +=" and o.orderNo like :orderNo";
					params.put("orderNo", "%"+orderInfo.getOrderNo()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
					hql +=" and o.productClient like :productClient";
					params.put("productClient", "%"+orderInfo.getProductClient()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getClientName())) {
					hql +=" and o.clientName like :clientName";
					params.put("clientName", "%"+orderInfo.getClientName()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getSeller())) {
					hql +=" and o.seller like :seller";
					params.put("seller", "%"+orderInfo.getSeller()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getMerchandiser())) {
					hql +=" and o.merchandiser like :merchandiser";
					params.put("merchandiser", "%"+orderInfo.getMerchandiser()+"%");
				}
			}
		}
		hql += " order by t.uploadtime desc";
		return this.findByAlias(hql, params);
	}
	@Override
	public Pager<Document> dataGrid(Document document, Set<Integer> states) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "";
		if (SystemContext.getCurrUserId() != null && SystemContext.getCurrUserId() != 0) {
			if (document != null && document.isUnread()) {//当前用户未读
				sql = "select t.id, t.id as unread, t.version, t.material_code as codeMt,"
						+ " t.name_mt as nameMt, t.uploadtime, t.state,"
						+ " o.order_no as orderNo,"
						+ " o.product_client as productClient,"
						+ " o.product_factory as productFactory,"
						+ " t.path"
						+ " from order_info o join document t on o.id = t.orderid RIGHT JOIN document_unread u ON t.id = u.doc_id WHERE u.user_id=:curruserid";
				params.put("curruserid", SystemContext.getCurrUserId());
				SystemContext.removeCurrUserId();
			} else {//当前用户所有
				sql = "select t.id,"
						+ " (SELECT 1 FROM document_unread u WHERE u.user_id="+SystemContext.getCurrUserId()+" AND u.doc_id=t.id) as unread,"
						+ " t.version, t.material_code as codeMt, t.name_mt as nameMt, t.uploadtime, t.state,"
						+ " o.order_no as orderNo,"
						+ " o.product_client as productClient,"
						+ " o.product_factory as productFactory,"
						+ " t.path"
						+ " from order_info o join document t on o.id = t.orderid WHERE 1=1";
			}
		} else {//查询所有用户，不返回未读信息
			sql = "select t.id,"
					+ " t.version, t.material_code as codeMt, t.name_mt as nameMt, t.uploadtime, t.state,"
					+ " o.order_no as orderNo,"
					+ " o.product_client as productClient,"
					+ " o.product_factory as productFactory,"
					+ " t.path"
					+ " from order_info o join document t on o.id = t.orderid WHERE 1=1";
		}
		if (states != null) {
			if (!states.isEmpty()) {
				sql +=" and t.state in ("+org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
			}
		}
		if (document != null) {
			if (document.getUserId() != null) {
				sql +=" and t.user_id=:userId";
				params.put("userId", document.getUserId());
			}
			if (document.getType() != null) {
				sql +=" and t.type=:type";
				params.put("type", document.getType());
			}
			if (StringUtils.isNotBlank(document.getOrderNo())) {
				sql +=" and o.order_no like :orderNo";
				params.put("orderNo", "%"+document.getOrderNo()+"%");
			}
			OrderInfo orderInfo = document.getOrderInfo();
			if (orderInfo != null) {
				if (orderInfo.getId() != null) {
					sql +=" and o.id=:orderId";
					params.put("orderId", orderInfo.getId());
				}
				if (StringUtils.isBlank(document.getOrderNo()) && StringUtils.isNotBlank(orderInfo.getOrderNo())) {
					sql +=" and o.order_no like :orderNo";
					params.put("orderNo", "%"+orderInfo.getOrderNo()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
					sql +=" and o.product_client like :productClient";
					params.put("productClient", "%"+orderInfo.getProductClient()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getClientName())) {
					sql +=" and o.client_name like :clientName";
					params.put("clientName", "%"+orderInfo.getClientName()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getSeller())) {
					sql +=" and o.seller like :seller";
					params.put("seller", "%"+orderInfo.getSeller()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getMerchandiser())) {
					sql +=" and o.merchandiser like :merchandiser";
					params.put("merchandiser", "%"+orderInfo.getMerchandiser()+"%");
				}
			}
		}
		
		sql += " order by t.uploadtime desc";
		return this.findByAliasSql(sql, params, Document.class, false);
	}

	@Override
	public List<Document> isExistPath(String path) {
		String hql = "select t from Document t join fetch t.orderInfo o where t.path=?";
		return this.listPage(hql, path, null);
	}

	@Override
	public Document isExistDocument(int orderId, String mtName) {
		String hql = "select t from Document t where t.nameMt=:mtName and t.orderInfo.id=:orderId";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("mtName", mtName);
		params.put("orderId", orderId);
		return (Document) this.queryObjectByAlias(hql, params);
	}

	@Override
	public Document getDocDetail(int docId) {
		String hql = "select t from Document t join fetch t.orderInfo o where t.id=?";
		return (Document) this.queryObject(hql, docId);
	}
}
