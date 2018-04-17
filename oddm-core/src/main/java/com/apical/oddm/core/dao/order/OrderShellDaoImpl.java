package com.apical.oddm.core.dao.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderShell;

@Repository("orderShellDao")
public class OrderShellDaoImpl extends BaseDaoImpl<OrderShell> implements OrderShellDaoI {

	@Override
	public List<OrderShell> dataGrid(int orderId) {
		String hql = "select t from OrderShell t join t.orderInfo o where o.id=?";
		return this.list(hql, orderId);
	}
}
