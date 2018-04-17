package com.apical.oddm.core.dao.bom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.bom.BomUnread;
import com.apical.oddm.core.model.page.SystemContext;

@Repository("bomUnreadDao")
public class BomUnreadDaoImpl extends BaseDaoImpl<BomUnread> implements BomUnreadDaoI {

	@Override
	public void delete(int userId, int bomId) {
		String sql = "delete from bom_unread where bom_id="+bomId+" and user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllUserId(int userId) {
		String sql = "delete from bom_unread where user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllBomId(int bomId) {
		String sql = "delete from bom_unread where bom_id="+bomId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteMonth(int month) {
		String sql = "delete from bom_unread where updatetime<DATE_SUB(NOW(), INTERVAL "+month+" MONTH)";
		this.updateBySql(sql);
	}

	@Override
	public List<Object> listByBomId(int bomId) {
		String sql = "select t.user_id from bom_unread t where t.bom_id="+bomId;
		SystemContext.setPageSize(Integer.MAX_VALUE);
		return this.listPageSql(sql, null, null, null, false);
	}

}
