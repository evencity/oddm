package com.apical.oddm.core.dao.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderFitting;

@Repository("orderFittingDao")
public class OrderFittingDaoImpl extends BaseDaoImpl<OrderFitting> implements OrderFittingDaoI {

	@Override
	public List<OrderFitting> dataGrid(int orderId) {
		String hql = "select t from OrderFitting t join t.orderInfo o where o.id=?";
		return this.list(hql, orderId);
	}

}
