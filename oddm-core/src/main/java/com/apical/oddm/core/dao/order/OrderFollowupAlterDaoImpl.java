package com.apical.oddm.core.dao.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderFollowupAlter;
import com.apical.oddm.core.model.page.Pager;

@Repository("orderFollowupAlterDao")
public class OrderFollowupAlterDaoImpl extends BaseDaoImpl<OrderFollowupAlter> implements OrderFollowupAlterDaoI {

	@Override
	public List<OrderFollowupAlter> listGrid(int followUpId) {
		String hql = "select t from OrderFollowupAlter t join t.orderFollowup o where o.id=? order by t.timestamp desc";
		return this.list(hql, followUpId);
	}

	@Override
	public Pager<OrderFollowupAlter> dataGrid(int followUpId) {
		String hql = "select t from OrderFollowupAlter t join t.orderFollowup o where o.id=? order by t.timestamp desc";
		return this.find(hql, followUpId);
	}

}
