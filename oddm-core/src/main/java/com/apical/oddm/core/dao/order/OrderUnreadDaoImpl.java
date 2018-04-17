package com.apical.oddm.core.dao.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderUnread;
import com.apical.oddm.core.model.page.SystemContext;

@Repository("orderUnreadDao")
public class OrderUnreadDaoImpl extends BaseDaoImpl<OrderUnread> implements OrderUnreadDaoI {

	@Override
	public void delete(int userId, int orderId) {
		String sql = "delete from order_unread where order_id="+orderId+" and user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllUserId(int userId) {
		String sql = "delete from order_unread where user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllOrderId(int orderId) {
		String sql = "delete from order_unread where order_id="+orderId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteMonth(int month) {
		String sql = "delete from order_unread where updatetime<DATE_SUB(NOW(), INTERVAL "+month+" MONTH)";
		this.updateBySql(sql);
	}

	@Override
	public List<Object> listByOrderId(int orderId) {
		String sql = "select t.user_id from order_unread t where t.order_id="+orderId;
		SystemContext.setPageSize(Integer.MAX_VALUE);
		return this.listPageSql(sql, null, null, null, false);
	}

}
