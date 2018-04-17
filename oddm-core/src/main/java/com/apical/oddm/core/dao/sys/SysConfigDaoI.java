package com.apical.oddm.core.dao.sys;

import java.io.Serializable;

import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.SysConfig;

/**
 * 系统配置表dao操作接口
 * @author lgx
 * 2016-10-12
 */
public interface SysConfigDaoI {

	/**
	 * 分页获取所有配置项
	 * @return
	 */
	public Pager<SysConfig> dataGrid();

	/**
	 * 根据名称删除对象
	 * @param key
	 */
	public void delete(String key);

	/**
	 * 通过名称获取对象
	 * @param key
	 * @return
	 */
	public SysConfig get(String key);

	/**
	 * 增加
	 * @param sysConfig
	 * @return
	 */
	public Serializable add(SysConfig sysConfig);

	/**
	 * 修改
	 * @param sysConfig
	 */
	public void update(SysConfig sysConfig);
	
}
