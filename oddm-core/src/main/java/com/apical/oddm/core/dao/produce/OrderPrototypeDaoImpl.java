package com.apical.oddm.core.dao.produce;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderPrototype;

@Repository("orderPrototypeDao")
public class OrderPrototypeDaoImpl extends BaseDaoImpl<OrderPrototype> implements OrderPrototypeDaoI {

	
	@Override
	public OrderPrototype get(int id) {
		String hql = "select t from OrderPrototype t join fetch t.orderInfo o left join fetch t.orderPrototypeDetials s where t.id=?";
		return (OrderPrototype) this.queryObject(hql, id);
	}

	@Override
	public Pager<OrderPrototype> dataGrid() {
		String hql = "select t from OrderPrototype t join fetch t.orderInfo o order by t.updatetime desc";
		return this.find(hql);
	}

	@Override
	public Pager<OrderPrototype> dataGrid(OrderInfo orderInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from SalePo t join fetch t.orderInfo o where 1=1";
		if (orderInfo != null) {
			if (orderInfo.getId() != null) {
				hql +=" and o.id=:orderId";
				params.put("orderId", orderInfo.getId());
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
				hql +=" and o.orderNo=:orderNo";
				params.put("orderNo", orderInfo.getOrderNo());
			}
			if (StringUtils.isNotBlank(orderInfo.getProductFactory())) {
				hql +=" and o.productFactory=:productFactory";
				params.put("productFactory", orderInfo.getProductFactory());
			}
			if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
				hql +=" and o.productClient=:productClient";
				params.put("productClient", orderInfo.getProductClient());
			}
			if (StringUtils.isNotBlank(orderInfo.getClientName())) {
				hql +=" and o.clientName=:clientName";
				params.put("clientName", orderInfo.getClientName());
			}
			if (StringUtils.isNotBlank(orderInfo.getSeller())) {
				hql +=" and o.seller=:seller";
				params.put("seller", orderInfo.getSeller());
			}
			if (StringUtils.isNotBlank(orderInfo.getMerchandiser())) {
				hql +=" and o.merchandiser=:merchandiser";
				params.put("merchandiser", orderInfo.getMerchandiser());
			}
			/*if (orderInfo.getUser() != null) {
				if (orderInfo.getUser().getUsername() != null) {
					hql +=" and o.user.username=:username";
					params.put("username", orderInfo.getUser().getUsername());
				}
			}*/
		}
		hql += " order by t.updatetime desc";
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<OrderPrototype> dataGridByDateUpdate(Date startDate,
			Date endDate) {
		String hql = "select t from OrderPrototype t join fetch t.orderInfo o where t.uploadtime>=:startDate and t.uploadtime<=:endDate order by t.uploadtime desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<OrderPrototype> dataGrid(Set<Integer> states) {
		return this.find("select t from OrderPrototype t join fetch t.orderInfo o where t.state in ("
				+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ") order by t.uploadtime desc");
	}
	
}
