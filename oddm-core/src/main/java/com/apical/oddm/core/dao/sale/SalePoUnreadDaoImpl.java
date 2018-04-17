package com.apical.oddm.core.dao.sale;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sale.SalePoUnread;

@Repository("salePoUnreadDao")
public class SalePoUnreadDaoImpl extends BaseDaoImpl<SalePoUnread> implements SalePoUnreadDaoI {

	@Override
	public void delete(int userId, int poId) {
		String sql = "delete from sale_po_unread where po_id="+poId+" and user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllUserId(int userId) {
		String sql = "delete from sale_po_unread where user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllPoId(int poId) {
		String sql = "delete from sale_po_unread where po_id="+poId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteMonth(int month) {
		String sql = "delete from sale_po_unread where updatetime<DATE_SUB(NOW(), INTERVAL "+month+" MONTH)";
		this.updateBySql(sql);
	}

	@Override
	public List<Object> listByPoId(int poId) {
		String sql = "select t.user_id from sale_po_unread t where t.po_id="+poId;
		SystemContext.setPageSize(Integer.MAX_VALUE);
		return this.listPageSql(sql, null, null, null, false);
	}

}
