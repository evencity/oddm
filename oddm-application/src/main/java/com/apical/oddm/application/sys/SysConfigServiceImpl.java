package com.apical.oddm.application.sys;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.core.dao.sys.SysConfigDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.SysConfig;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigServiceI {

	@Autowired
	private SysConfigDaoI sysConfigDao;
	
	@Override
	public Pager<SysConfig> dataGrid() {
		return sysConfigDao.dataGrid();
	}

	@Override
	public Serializable add(SysConfig sysConfig) {
		return sysConfigDao.add(sysConfig);
	}

	@Override
	public void edit(SysConfig sysConfig) {
		sysConfigDao.update(sysConfig);
	}

	@Override
	public void delete(String key) {
		sysConfigDao.delete(key);
	}

	@Override
	public SysConfig get(String key) {
		return sysConfigDao.get(key);
	}

}
