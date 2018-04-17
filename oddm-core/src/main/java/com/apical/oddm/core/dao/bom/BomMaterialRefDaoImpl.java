package com.apical.oddm.core.dao.bom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.bom.BomMaterialRef;

@Repository("bomMaterialRefDao")
public class BomMaterialRefDaoImpl extends BaseDaoImpl<BomMaterialRef> implements BomMaterialRefDaoI {

	@Override
	public List<BomMaterialRef> getByBomId(int bomId) {
		String hql = "select t from BomMaterialRef t left join fetch t.contacts c where t.bomInfo.id=?";
		return this.list(hql, bomId);
	}

}
