package com.apical.oddm.core.dao.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;

@Repository("materialFollowupDao")
public class MaterialFollowupDaoImpl extends BaseDaoImpl<MaterialFollowup> implements MaterialFollowupDaoI {

	@Override
	public Pager<MaterialFollowup> dataGrid() {
		String hql = "select t from MaterialFollowup t join fetch t.orderInfo o order by t.timestamp desc";
		return this.find(hql);
	}

	@Override
	public Pager<MaterialFollowup> dataGrid(Set<Integer> states) {
		return this.find("select t from MaterialFollowup t join fetch t.orderInfo o where t.state in ("
				+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ") order by t.timestamp desc");
	}

	@Override
	public Pager<MaterialFollowup> dataGridByOrder(OrderInfo orderInfo, Set<Integer> states) {
		if ("t.id".equals(SystemContext.getSort())) {
			SystemContext.setSort("o.id");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from MaterialFollowup t join fetch t.orderInfo o where 1=1";
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
	}

	/*@Override
	public Pager<MaterialFollowup> dataGridByMaterialFollowup(MaterialFollowup materialFollowup) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from MaterialFollowup t join fetch t.orderInfo o where 1=1";
		if (materialFollowup != null) {
			if (materialFollowup.getMerchandiser() != null) {
				hql +=" and t.merchandiser=:merchandiser";
				params.put("merchandiser", materialFollowup.getMerchandiser());
			}
			if (materialFollowup.getMtCode() != null) {
				hql +=" and t.mtCode=:mtCode";
				params.put("mtCode", materialFollowup.getMtCode());
			}
		}
		hql += " order by t.timestamp desc";
		return this.findByAlias(hql, params);
	}*/

	@Override
	public Pager<MaterialFollowup> dataGridByDateDeliver(Date startDate, Date endDate) {
		String hql = "select t from MaterialFollowup t join fetch t.orderInfo o where t.dateDeliver>=:startDate and t.dateDeliver<=:endDate order by t.dateDeliver desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<MaterialFollowup> dataGridByDateCommit(Date startDate, Date endDate) {
		String hql = "select t from MaterialFollowup t join fetch t.orderInfo o where t.dateCommit>=:startDate and t.dateCommit<=:endDate order by t.dateCommit desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public MaterialFollowup getMaterialFollowup(int id) {
		String hql = "select t from MaterialFollowup t join fetch t.orderInfo o where t.id=?";
		return (MaterialFollowup) this.queryObject(hql, id);
	}

	@Override
	public List<MaterialFollowup> listAll(OrderInfo orderInfo,
			Set<Integer> states, boolean lazy) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "";
		if (lazy) {
			hql = "select distinct t from MaterialFollowup t join t.orderInfo o where 1=1";
		} else {
			hql = "select distinct t from MaterialFollowup t join fetch t.orderInfo o where 1=1";
		}
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
		hql += " order by t.timestamp asc";
		return this.listByAlias(hql, params);
	}

}
