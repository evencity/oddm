package com.apical.oddm.core.dao.produce;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.produce.ManufacturePackageTitle;

@Repository("manufacturePackageTitleDao")
public class ManufacturePackageTitleDaoImpl extends BaseDaoImpl<ManufacturePackageTitle> implements ManufacturePackageTitleDaoI {

	@Override
	public List<ManufacturePackageTitle> getListAll() {
		String hql = "select t from ManufacturePackageTitle t order by t.id asc";
		return this.list(hql);
	}

}
