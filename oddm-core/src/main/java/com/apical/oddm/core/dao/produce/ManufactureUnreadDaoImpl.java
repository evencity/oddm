package com.apical.oddm.core.dao.produce;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.produce.ManufactureUnread;

@Repository("manufactureUnreadDao")
public class ManufactureUnreadDaoImpl extends BaseDaoImpl<ManufactureUnread> implements ManufactureUnreadDaoI {

	@Override
	public void delete(int userId, int manufactureId) {
		String sql = "delete from order_mft_unread where mft_id="+manufactureId+" and user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllUserId(int userId) {
		String sql = "delete from order_mft_unread where user_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteAllManufactureId(int manufactureId) {
		String sql = "delete from order_mft_unread where mft_id="+manufactureId;
		this.updateBySql(sql);
	}

	@Override
	public void deleteMonth(int month) {
		String sql = "delete from order_mft_unread where updatetime<DATE_SUB(NOW(), INTERVAL "+month+" MONTH)";
		this.updateBySql(sql);
	}

	@Override
	public List<Object> listByManufactureId(int manufactureId) {
		String sql = "select t.user_id from order_mft_unread t where t.mft_id="+manufactureId;
		SystemContext.setPageSize(Integer.MAX_VALUE);
		return this.listPageSql(sql, null, null, null, false);
	}

}
