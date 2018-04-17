package com.apical.oddm.application.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.produce.ManufactureAlterDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.ManufactureAlt;

@Service("manufactureAlterService")
public class ManufactureAlterServiceImpl extends BaseServiceImpl<ManufactureAlt> implements ManufactureAlterServiceI {

	@Autowired
	private ManufactureAlterDaoI manufactureAlterDao;

	@Override
	public Pager<ManufactureAlt> dataGrid(int mftId) {
		return manufactureAlterDao.dataGrid(mftId);
	}

}
