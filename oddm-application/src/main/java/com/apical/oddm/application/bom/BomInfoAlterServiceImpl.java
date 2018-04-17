package com.apical.oddm.application.bom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.bom.BomInfoAlterDaoI;
import com.apical.oddm.core.model.bom.BomInfoAlt;
import com.apical.oddm.core.model.page.Pager;

@Service("bomInfoAlterService")
public class BomInfoAlterServiceImpl extends BaseServiceImpl<BomInfoAlt> implements BomInfoAlterServiceI {

	@Autowired
	private BomInfoAlterDaoI bomInfoAlterDao;
	
	@Override
	public List<BomInfoAlt> listGrid(int bominfoId) {
		return bomInfoAlterDao.listGrid(bominfoId);
	}

	@Override
	public Pager<BomInfoAlt> dataGrid(int bominfoId) {
		return bomInfoAlterDao.dataGrid(bominfoId);
	}

}
