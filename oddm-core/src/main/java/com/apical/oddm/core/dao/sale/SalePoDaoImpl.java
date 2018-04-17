package com.apical.oddm.core.dao.sale;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sale.SalePo;

@Repository("salePoDao")
public class SalePoDaoImpl extends BaseDaoImpl<SalePo> implements SalePoDaoI {

	@Override
	public Pager<SalePo> dataGrid() {
		String hql = "select t from SalePo t join fetch t.orderInfo o order by t.updatetime desc";
		return this.find(hql);
	}

	@Override
	public SalePo getSalePo(int id) {
		String hql = "select t from SalePo t join fetch t.orderInfo o left join fetch t.salePoLists s where t.id=?";
		return (SalePo) this.queryObject(hql, id);
	}


	@Override
	public SalePo getByOrderId(int orderId, boolean lazy) {
		String  hql = null;
		if (lazy) {
			hql = "select t from SalePo t where t.orderInfo.id=?";
		} else {
			hql = "select t from SalePo t join fetch t.orderInfo o left join fetch t.salePoLists s where o.id=?";
		}
		return (SalePo) this.queryObject(hql, orderId);
	}
	
	@Override
	@Deprecated
	public Pager<SalePo> dataGrid(OrderInfo orderInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from SalePo t join fetch t.orderInfo o where 1=1";
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
		hql += " order by t.updatetime desc";
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<SalePo> dataGridByDateDelivery(Date startDate, Date endDate) {
		String hql = "select t from SalePo t join fetch t.orderInfo o where o.dateDelivery>=:startDate and o.dateDelivery<=:endDate order by o.dateDelivery desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<SalePo> dataGridByDateOrder(Date startDate, Date endDate) {
		String hql = "select t from SalePo t join fetch t.orderInfo o where o.dateOrder>=:startDate and o.dateOrder<=:endDate order by o.dateOrder desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<SalePo> dataGridByDateUpdate(Date startDate, Date endDate) {
		String hql = "select t from SalePo t join fetch t.orderInfo o where t.uploadtime>=:startDate and t.uploadtime<=:endDate order by t.uploadtime desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}


	@Override
	public Pager<SalePo> dataGrid(SalePo salePo, Set<Integer> states) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "";
		if (SystemContext.getCurrUserId() != null && SystemContext.getCurrUserId() != 0) {
			if (salePo != null && salePo.isUnread()) {//当前用户未读
				sql = "select t.id, t.id as unread,"
						+ " t.pi_no as piNo, t.pm,"
						+ " t.description, t.currency, t.maker, t.approver, t.approver_id as approverId, t.date_examine as dateExaminePre,"
						+ " t.timestamp, t.updatetime, t.state, t.company, t.address, t.tel, t.fax, t.homepage,"
						+ " o.order_no AS orderNo,"
						+ " o.merchandiser, o.seller,"
						+ " o.product_factory AS productFactory,"
						+ " o.product_client AS productClient,"
						+ " o.client_name_code AS clientNameCode,"
						+ " o.client_name AS clientName,"
						+ " o.date_order AS dateOrder,"
						+ " o.date_delivery AS dateDelivery"
						+ " from order_info o join sale_po t on o.id = t.order_id RIGHT JOIN sale_po_unread u ON t.id = u.po_id WHERE u.user_id=:curruserid";
				params.put("curruserid", SystemContext.getCurrUserId());
				SystemContext.removeCurrUserId();
			} else {//当前用户所有
				sql = "select t.id,"
						+ " (SELECT u.user_id FROM sale_po_unread u WHERE u.user_id="+SystemContext.getCurrUserId()+" AND u.po_id=t.id) AS unread,"
						+ " t.pi_no as piNo, t.pm,"
						+ " t.description, t.currency, t.maker, t.approver, t.approver_id AS approverId, t.date_examine as dateExaminePre,"
						+ " t.timestamp, t.updatetime, t.state, t.company, t.address, t.tel, t.fax, t.homepage,"
						+ " o.order_no AS orderNo,"
						+ " o.merchandiser, o.seller,"
						+ " o.product_factory AS productFactory,"
						+ " o.product_client AS productClient,"
						+ " o.client_name_code AS clientNameCode,"
						+ " o.client_name AS clientName,"
						+ " o.date_order AS dateOrder,"
						+ " o.date_delivery AS dateDelivery"
						+ " from order_info o join sale_po t on o.id = t.order_id WHERE 1=1";
			}
		} else {//查询所有用户，不返回未读信息
			sql = "select t.id,"
					+ " t.description, t.currency, t.maker, t.approver, t.approver_id as approverId, t.date_examine as dateExaminePre,"
					+ " t.timestamp, t.updatetime, t.state, t.company, t.address, t.tel, t.fax, t.homepage,"
					+ " o.order_no AS orderNo,"
					+ " o.merchandiser, o.seller,"
					+ " o.product_factory AS productFactory,"
					+ " o.product_client AS productClient,"
					+ " o.client_name_code AS clientNameCode,"
					+ " o.client_name AS clientName,"
					+ " o.date_order AS dateOrder,"
					+ " o.date_delivery AS dateDelivery"
					+ " from order_info o join sale_po t on o.id = t.order_id WHERE 1=1";
		}
		if (states != null && !states.isEmpty()) {
			sql +=" and t.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		if (salePo.getOrderInfo() != null) {
			OrderInfo orderInfo = salePo.getOrderInfo();
			if (orderInfo.getId() != null) {
				sql +=" and o.id=:orderId";
				params.put("orderId", orderInfo.getId());
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
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
			if (orderInfo.getSellerId() != null && orderInfo.getMerchandiserId() != null) {
				sql +=" and (o.seller_id=:sellerId";
				params.put("sellerId", orderInfo.getSellerId());
				sql +=" or o.merchandiser_id=:merchandiserId)";
				params.put("merchandiserId", orderInfo.getMerchandiserId());
			} else {
				if (orderInfo.getSellerId() != null) {
					sql +=" and o.seller_id=:sellerId";
					params.put("sellerId", orderInfo.getSellerId());
				}
				if (orderInfo.getMerchandiserId() != null) {
					sql +=" and o.merchandiser_id=:merchandiserId";
					params.put("merchandiserId", orderInfo.getMerchandiserId());
				}
			}
		}
		if (salePo != null) {
			if (salePo.getApproverId() != null) {
				sql +=" and t.approver_id=:approverId";
				params.put("approverId", salePo.getApproverId());
			}
			if (StringUtils.isNotBlank(salePo.getPiNo())) {
				sql +=" and t.pi_no like :piNo";
				params.put("piNo", "%"+salePo.getPiNo()+"%");
			}
			if (StringUtils.isNotBlank(salePo.getPm())) {
				sql +=" and t.pm like :pm";
				params.put("pm", "%"+salePo.getPm()+"%");
			}
			if (StringUtils.isNotBlank(salePo.getMaker())) {
				sql +=" and t.maker like :maker";
				params.put("maker", "%"+salePo.getMaker()+"%");
			}
		}
		sql += " order by t.updatetime desc";
		String sort = SystemContext.getSort();
		String order = SystemContext.getOrder();
		if ("t.updatetime".equals(sort)) {
			if ("asc".equals(order)) {
				SystemContext.setOrder("desc");
			} else {
				SystemContext.setOrder("asc");
			}
		} else if ("t.orderNo".equals(sort)) {
			SystemContext.setSort("o.order_no");
		} else if ("t.piNo".equals(sort)) {
			SystemContext.setSort("t.pi_no");
		}
		return this.findByAliasSql(sql, params, SalePo.class, false);
	}
	@Override
	public SalePo getLatelyCompany(Integer sellerId, Integer merchandiserId) {
		String sql = null;
		/****************查自己的***************/
		if (sellerId !=null && merchandiserId != null) {
			sql = "select t.id, t.company, t.address, t.fax, t.tel, t.homepage from sale_po t, order_info o where t.order_id=o.id and (o.seller_id=78 or o.merchandiser_id=61) order by t.id desc limit 1";
		} else {
			if (sellerId != null) {
				sql = "select t.id, t.company, t.address, t.fax, t.tel, t.homepage from sale_po t, order_info o where t.order_id=o.id and o.seller_id="+sellerId+" order by t.id desc limit 1";
			} else if (merchandiserId != null) {
				sql = "select t.id, t.company, t.address, t.fax, t.tel, t.homepage from sale_po t, order_info o where t.order_id=o.id and o.merchandiser_id="+merchandiserId+" order by t.id desc limit 1";
			}
		}
		List<SalePo> listBySql = this.listBySql(sql, SalePo.class, false);
		if(listBySql != null && !listBySql.isEmpty()) {
			return listBySql.get(0);
		}
		/****************再单独查一次主表***************/
		sql = "select t.id, t.company, t.address, t.fax, t.tel, t.homepage from sale_po t order by t.id desc limit 1";
		listBySql = this.listBySql(sql, SalePo.class, false);
		if(listBySql != null && !listBySql.isEmpty()) {
			return listBySql.get(0);
		}
		return null;
	}

	@Override
	public void updateUpdateTime(int id) {
		String sql = "update sale_po set updatetime=now() where id="+id;
		this.updateBySql(sql);
	}
}

/*@Override
public Pager<SalePo> dataGrid(SalePo salePo, Set<Integer> states) {
	Map<String, Object> params = new HashMap<String, Object>();
	String hql = "select t from SalePo t join fetch t.orderInfo o where 1=1";
	if (states != null && !states.isEmpty()) {
		hql +=" and t.state in ("
		+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
	}
	if (salePo.getOrderInfo() != null) {
		OrderInfo orderInfo = salePo.getOrderInfo();
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
		if (orderInfo.getSellerId() != null && orderInfo.getMerchandiserId() != null) {
			hql +=" and (o.sellerId=:sellerId";
			params.put("sellerId", orderInfo.getSellerId());
			hql +=" or o.merchandiserId=:merchandiserId)";
			params.put("merchandiserId", orderInfo.getMerchandiserId());
		} else {
			if (orderInfo.getSellerId() != null) {
				hql +=" and o.sellerId=:sellerId";
				params.put("sellerId", orderInfo.getSellerId());
			}
			if (orderInfo.getMerchandiserId() != null) {
				hql +=" and o.merchandiserId=:merchandiserId";
				params.put("merchandiserId", orderInfo.getMerchandiserId());
			}
		}
	}
	if (salePo != null) {
		if (salePo.getApproverId() != null) {
			hql +=" and t.approverId=:approverId";
			params.put("approverId", salePo.getApproverId());
		}
		if (StringUtils.isNotBlank(salePo.getPiNo())) {
			hql +=" and t.piNo like :piNo";
			params.put("piNo", "%"+salePo.getPiNo()+"%");
		}
		if (StringUtils.isNotBlank(salePo.getPm())) {
			hql +=" and t.pm like :pm";
			params.put("pm", "%"+salePo.getPm()+"%");
		}
		if (StringUtils.isNotBlank(salePo.getMaker())) {
			hql +=" and t.maker like :maker";
			params.put("maker", "%"+salePo.getMaker()+"%");
		}
	}
	hql += " order by t.updatetime desc";
	return this.findByAlias(hql, params);
}*/
