package com.apical.oddm.core.dao.produce;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.ManufactureAlt;

@Repository("manufactureAlterDao")
public class ManufactureAlterDaoImpl extends BaseDaoImpl<ManufactureAlt> implements ManufactureAlterDaoI {

	@Override
	public Pager<ManufactureAlt> dataGrid(int mftId) {
		String hql = "select t from ManufactureAlt t join t.manufacture o where o.id=? order by t.timestamp desc";
		return this.find(hql, mftId);
	}

}
