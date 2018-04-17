package com.apical.oddm.core.dao.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;

@Repository("orderFollowupDao")
public class OrderFollowupDaoImpl extends BaseDaoImpl<OrderFollowup> implements OrderFollowupDaoI {

	
	@Override
	public OrderFollowup get(int id) {
		String hql = "select t from OrderFollowup t join fetch t.orderInfo o where t.id=?";
		OrderFollowup queryObject = (OrderFollowup)this.queryObject(hql, id);
		/*System.err.println("queryObject "+queryObject.getOrderFollowupOuts());
		System.err.println("queryObjec2t "+queryObject.getShipmentDatelast());
		System.err.println("queryObject "+queryObject.getShipmentTotal());*/
		return queryObject;
	}

/*	@Override
	public Pager<OrderFollowup> dataGrid() {
		String hql = "select t from OrderFollowup t join fetch t.orderInfo o order by t.timestamp desc";
		return this.find(hql);
	}

	@Override
	public Pager<OrderFollowup> dataGrid(Set<Integer> states) {
		return this.find("select t from OrderFollowup t join fetch t.orderInfo o where t.state in ("
				+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ") order by t.timestamp desc");
	}*/
	
	/*@Override
	public Pager<OrderFollowup> dataGridByOrder(OrderInfo orderInfo, Set<Integer> states) {
		if ("t.id".equals(SystemContext.getSort())) {
			SystemContext.setSort("o.id");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from OrderFollowup t left join fetch t.orderInfo o where 1=1";
		if (states != null && !states.isEmpty()) {
			hql +=" and t.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		if (orderInfo != null) {
			if (orderInfo.getId() != null) {
				hql +=" and o.id=:orderId";
				params.put("orderId", orderInfo.getId());
			}
			if (StringUtils.isNotBlank(orderInfo.getClientName())) {
				hql +=" and o.clientName like :clientName";
				params.put("clientName", "%"+orderInfo.getClientName()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getClientNameCode())) {
				hql +=" and o.clientNameCode like :clientNameCode";
				params.put("clientNameCode", "%"+orderInfo.getClientNameCode()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductFactory())) {
				hql +=" and o.productFactory like :productFactory";
				params.put("productFactory", "%"+orderInfo.getProductFactory()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
				hql +=" and o.productClient like :productClient";
				params.put("productClient", "%"+orderInfo.getProductClient()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
				hql +=" and o.orderNo like :orderNo";
				params.put("orderNo", "%"+orderInfo.getOrderNo()+"%");
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
		hql += " order by t.timestamp desc";
		return this.findByAlias(hql, params);
	}*/

	@Override
	public Pager<OrderFollowup> dataGridByOrder(OrderInfo orderInfo, Set<Integer> states) {
		if ("t.id".equals(SystemContext.getSort())) {
			SystemContext.setSort("o.id");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select t.id, t.state, t.description, t.specification, t.timestamp, t.updatetime, t.order_id as orderId,"
				+ " t.date_client as dateClient, t.date_factory as dateFactory,"
				+ " o.order_no AS orderNo, o.seller_id AS sellerId, o.seller,"
				+ " o.merchandiser_id AS merchandiserId, o.merchandiser, o.product_factory AS productFactory,"
				+ " o.product_client AS productClient, o.quantity, o.client_name AS clientName,"
				+ " o.client_name_code AS clientNameCode, o.district, o.date_order AS dateOrder, o.date_delivery AS dateDelivery"
				+ " from order_followup t"
				+ " left join order_info o on t.order_id=o.id"
				+ " where 1=1";
		if (states != null && !states.isEmpty()) {
			sql +=" and t.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		if (orderInfo != null) {
			if (orderInfo.getId() != null) {
				sql +=" and o.id=:orderId";
				params.put("orderId", orderInfo.getId());
			}
			if (StringUtils.isNotBlank(orderInfo.getClientName())) {
				sql +=" and o.client_name like :clientName";
				params.put("clientName", "%"+orderInfo.getClientName()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getClientNameCode())) {
				sql +=" and o.client_name_code like :clientNameCode";
				params.put("clientNameCode", "%"+orderInfo.getClientNameCode()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductFactory())) {
				sql +=" and o.product_factory like :productFactory";
				params.put("productFactory", "%"+orderInfo.getProductFactory()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
				sql +=" and o.product_client like :productClient";
				params.put("productClient", "%"+orderInfo.getProductClient()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
				sql +=" and o.order_no like :orderNo";
				params.put("orderNo", "%"+orderInfo.getOrderNo()+"%");
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
		sql += " order by t.timestamp desc";
		Pager<OrderFollowup> pager = this.findByAliasSql(sql, params, OrderFollowup.class, false);
		if (pager.getRows() != null && !pager.getRows().isEmpty()) {
			for (OrderFollowup up : pager.getRows()) {
				OrderInfo o = new OrderInfo();
				BeanUtils.copyProperties(up, o);
				o.setDescription(null);
				o.setState(null);
				o.setId(up.getOrderId());
				up.setOrderInfo(o);
				//System.err.println("up2" +o.getOrderNo());
			}
		}
		return pager;
	}
	@Override
	public Pager<OrderFollowup> dataGridByOrderFollowup(OrderFollowup orderFollowup) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from OrderFollowup t join fetch t.orderInfo o where 1=1";
       
		hql += " order by t.timestamp desc";
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<OrderFollowup> dataGridByDateClient(Date startDate, Date endDate) {
		String hql = "select t from OrderFollowup t join fetch t.orderInfo o where t.dateClient>=:startDate and t.dateClient<=:endDate order by t.dateClient desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<OrderFollowup> dataGridByDateOrder(Date startDate, Date endDate) {
		String hql = "select t from OrderFollowup t join fetch t.orderInfo o where o.dateOrder>=:startDate and o.dateOrder<=:endDate order by o.dateOrder desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public OrderFollowup getByOrderInfo(OrderInfo orderInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from OrderFollowup t join fetch t.orderInfo o where 1=1";
		if (orderInfo != null) {
			if (orderInfo.getId() != null) {
				hql +=" and o.id=:orderId";
				params.put("orderId", orderInfo.getId());
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
				hql +=" and o.orderNo=:orderNo";
				params.put("orderNo", orderInfo.getOrderNo());
			}
			if (orderInfo.getMerchandiserId() != null) {
				hql +=" and o.merchandiser_id=:merchandiserId";
				params.put("merchandiserId", orderInfo.getMerchandiserId());
			}
			return (OrderFollowup) this.queryObjectByAlias(hql, params);
		}
		return null;
	}

	@Override
	public List<OrderFollowup> listAll(OrderInfo orderInfo, Set<Integer> states) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select distinct t from OrderFollowup t join fetch t.orderInfo o where 1=1";
		if (states != null && !states.isEmpty()) {
			hql +=" and t.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		if (orderInfo != null) {
			if (orderInfo.getId() != null) {
				hql +=" and o.id=:orderId";
				params.put("orderId", orderInfo.getId());
			}
			if (StringUtils.isNotBlank(orderInfo.getClientName())) {
				hql +=" and o.clientName=:clientName";
				params.put("clientName", orderInfo.getClientName());
			}
			if (orderInfo.getDateOrderStart() != null  && orderInfo.getDateOrderEnd() != null) {
				hql +=" and o.dateOrder>=:startDate and o.dateOrder<=:endDate";
				params.put("startDate", orderInfo.getDateOrderStart());
				params.put("endDate", orderInfo.getDateOrderEnd());
			}
			if (StringUtils.isNotBlank(orderInfo.getClientNameCode())) {
				hql +=" and o.clientNameCode=:clientNameCode";
				params.put("clientNameCode", orderInfo.getClientNameCode());
			}
			if (StringUtils.isNotBlank(orderInfo.getProductFactory())) {
				hql +=" and o.productFactory=:productFactory";
				params.put("productFactory", orderInfo.getProductFactory());
			}
			if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
				hql +=" and o.productClient=:productClient";
				params.put("productClient", orderInfo.getProductClient());
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
				hql +=" and o.orderNo=:orderNo";
				params.put("orderNo", orderInfo.getOrderNo());
			}
			if (StringUtils.isNotBlank(orderInfo.getSeller())) {
				hql +=" and o.seller=:seller";
				params.put("seller", orderInfo.getSeller());
			}
			if (StringUtils.isNotBlank(orderInfo.getMerchandiser())) {
				hql +=" and o.merchandiser=:merchandiser";
				params.put("merchandiser", orderInfo.getMerchandiser());
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
		hql += " order by t.timestamp desc";
		return this.listByAlias(hql, params);
	}
	@Override
	public void updateUpdateTime(int id) {
		String sql = "update order_followup set updatetime=now() where id="+id;
		this.updateBySql(sql);
	}
}
