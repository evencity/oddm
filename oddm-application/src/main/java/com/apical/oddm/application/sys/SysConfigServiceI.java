package com.apical.oddm.application.sys;

import java.io.Serializable;

import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.SysConfig;


/**
 * 系统配置表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface SysConfigServiceI {
	
	/**
	 * 获取所有列表
	 * @return
	 */
	public Pager<SysConfig> dataGrid();
	
	/**
	 * 添加对象
	 * @param sysConfig
	 * @return 主键
	 */
	public Serializable add(SysConfig sysConfig);
	/**
	 * 编辑对象
	 * @param sysConfig
	 */
	public void edit(SysConfig sysConfig);
	/**
	 * 根据名称删除对象
	 * @param key
	 */
	public void delete(String key);
	/**
	 * 根据名称加载对象
	 * @param key
	 * @return
	 */
	public SysConfig get(String key);

}
