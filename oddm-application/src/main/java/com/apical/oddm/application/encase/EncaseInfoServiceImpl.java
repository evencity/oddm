package com.apical.oddm.application.encase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.encase.EncaseInfoDaoI;
import com.apical.oddm.core.model.encase.EncaseInfo;
import com.apical.oddm.core.model.page.Pager;

@Service("encaseInfoService")
public class EncaseInfoServiceImpl extends BaseServiceImpl<EncaseInfo> implements EncaseInfoServiceI {

	@Autowired
	private EncaseInfoDaoI encaseInfoDao;
	
	@Override
	public Pager<EncaseInfo> dataGrid(EncaseInfo encaseInfo) {
		return encaseInfoDao.dataGrid(encaseInfo);
	}

	@Override
	public EncaseInfo getDetail(int encaseInfoId) {
		return encaseInfoDao.getDetail(encaseInfoId);
	}

	@Override
	public EncaseInfo getLatestRow() {
		return encaseInfoDao.getLatestRow();
	}

}
