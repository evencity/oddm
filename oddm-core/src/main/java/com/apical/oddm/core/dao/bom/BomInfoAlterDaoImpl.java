package com.apical.oddm.core.dao.bom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.bom.BomInfoAlt;
import com.apical.oddm.core.model.page.Pager;

@Repository("bomInfoAlterDao")
public class BomInfoAlterDaoImpl extends BaseDaoImpl<BomInfoAlt> implements BomInfoAlterDaoI {

	@Override
	public List<BomInfoAlt> listGrid(int bominfoId) {
		String hql = "select t from BomInfoAlt t join t.bomInfo o where o.id=? order by t.timestamp asc";
		return this.list(hql, bominfoId);
	}

	@Override
	public Pager<BomInfoAlt> dataGrid(int bominfoId) {
		String hql = "select t from BomInfoAlt t join t.bomInfo o where o.id=? order by t.timestamp asc";
		return this.find(hql, bominfoId);
	}

}
