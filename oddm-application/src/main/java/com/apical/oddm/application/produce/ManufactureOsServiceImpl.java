package com.apical.oddm.application.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.produce.ManufactureOsDaoI;
import com.apical.oddm.core.model.produce.ManufactureOs;

@Service("manufactureOsService")
public class ManufactureOsServiceImpl extends BaseServiceImpl<ManufactureOs> implements ManufactureOsServiceI {

	@Autowired
	private ManufactureOsDaoI manufactureOsDao;
	
	@Override
	public ManufactureOs getByMftId(int mftId) {
		return manufactureOsDao.getByMftId(mftId);
	}

}
