package com.apical.oddm.core.dao.order;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.MaterialFollowupBase;

@Repository("materialFollowupBaseDao")
public class MaterialFollowupBaseDaoImpl extends BaseDaoImpl<MaterialFollowupBase> implements MaterialFollowupBaseDaoI {

	@Override
	public MaterialFollowupBase get() {
		String hql = "select t from MaterialFollowupBase t ";
		return (MaterialFollowupBase) this.queryObject(hql);
	}

}
