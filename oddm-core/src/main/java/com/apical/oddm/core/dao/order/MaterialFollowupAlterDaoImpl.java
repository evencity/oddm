package com.apical.oddm.core.dao.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.MaterialFollowupAlter;
import com.apical.oddm.core.model.page.Pager;

@Repository("materialFollowupAlterDao")
public class MaterialFollowupAlterDaoImpl extends BaseDaoImpl<MaterialFollowupAlter> implements MaterialFollowupAlterDaoI {

	@Override
	public List<MaterialFollowupAlter> listGrid(int followUpId) {
		String hql = "select t from MaterialFollowupAlter t join t.materialFollowup o where o.id=? order by t.timestamp desc";
		return this.list(hql, followUpId);
	}

	@Override
	public Pager<MaterialFollowupAlter> dataGrid(int followUpId) {
		String hql = "select t from MaterialFollowupAlter t join t.materialFollowup o where o.id=? order by t.timestamp desc";
		return this.find(hql, followUpId);
	}

}
