package com.apical.oddm.core.dao.sys;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.SysConfig;

@Repository("sysConfigDao")
public class SysConfigDaoImpl extends BaseDaoImpl<SysConfig> implements SysConfigDaoI {

	@Override
	public Pager<SysConfig> dataGrid() {
		String hql ="from SysConfig t";
		return this.find(hql);
	}

	@Override
	public void delete(String key) {
		String hql="delete SysConfig t where t.key=?";
		this.updateByHql(hql, key);
	}

	@Override
	public SysConfig get(String key) {
		String hql ="from SysConfig t where t.key=?";
		return (SysConfig) this.queryObject(hql, key);
	}
}
