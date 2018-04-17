package com.apical.oddm.core.dao.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderInfoAlt;
import com.apical.oddm.core.model.page.Pager;

@Repository("orderInfoAlterDao")
public class OrderInfoAlterDaoImpl extends BaseDaoImpl<OrderInfoAlt> implements OrderInfoAlterDaoI {

	@Override
	public List<OrderInfoAlt> listGrid(int orderId) {
		String hql = "select t from OrderInfoAlt t join t.orderInfo o where o.id=? order by t.timestamp desc";
		return this.list(hql, orderId);
	}

	@Override
	public Pager<OrderInfoAlt> dataGrid(int orderId) {
		String hql = "select t from OrderInfoAlt t join t.orderInfo o where o.id=? order by t.timestamp desc";
		return this.find(hql, orderId);
	}

}
