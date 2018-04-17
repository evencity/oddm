package com.apical.oddm.core.dao.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderPacking;

@Repository("orderPackingDao")
public class OrderPackingDaoImpl extends BaseDaoImpl<OrderPacking> implements OrderPackingDaoI {

	@Override
	public List<OrderPacking> dataGrid(int orderId) {
		String hql = "select t from OrderPacking t join t.orderInfo o where o.id=?";
		return this.list(hql, orderId);
	}
}
