package com.apical.oddm.core.dao.produce;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.produce.ManufactureOs;

@Repository("manufactureOsDao")
public class ManufactureOsDaoImpl extends BaseDaoImpl<ManufactureOs> implements ManufactureOsDaoI {

	@Override
	public ManufactureOs getByMftId(int mftId) {
		String hql = "select t from ManufactureOs t join fetch t.orderManufacture m where m.id=?";
		return (ManufactureOs) this.queryObject(hql, mftId);
	}

}
