package com.apical.oddm.core.dao.produce;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderRecord;

@Repository("orderRecordDao")
public class OrderRecordDaoImpl extends BaseDaoImpl<OrderRecord> implements OrderRecordDaoI {

	@Override
	public Pager<OrderRecord> dataGrid() {
		String hql = "select t from OrderRecord t join fetch t.orderInfo o join fetch t.orderRecordType s order by t.updatetime desc";
		return this.find(hql);
	}

	@Override
	public OrderRecord get(int id) {
		String hql = "select t from OrderRecord t join fetch t.orderInfo o join fetch t.orderRecordType s where t.id=?";
		return (OrderRecord) this.queryObject(hql, id);
	}


	@Override
	public Pager<OrderRecord> dataGrid(OrderInfo orderInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from OrderRecord t join fetch t.orderInfo o join fetch t.orderRecordType s where 1=1";
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
	public Pager<OrderRecord> dataGridByDateUpdate(Date startDate, Date endDate) {
		String hql = "select t from OrderRecord t join fetch t.orderInfo o join fetch t.orderRecordType s where t.uploadtime>=:startDate and t.uploadtime<=:endDate order by t.uploadtime desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<OrderRecord> dataGridByTypeId(int typeId) {
		String hql = "select t from OrderRecord t join fetch t.orderInfo o join fetch t.orderRecordType s where s.id=? order by t.uploadtime desc";
		return this.find(hql, typeId);
	}

	@Override
	public Pager<OrderRecord> dataGridByTypeName(int typeName) {
		String hql = "select t from OrderRecord t join fetch t.orderInfo o join fetch t.orderRecordType s where s.name=? order by t.uploadtime desc";
		return this.find(hql, typeName);
	}

}
