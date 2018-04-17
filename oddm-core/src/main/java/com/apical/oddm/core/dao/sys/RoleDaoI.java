package com.apical.oddm.core.dao.sys;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Role;

/**
 * 角色表Dao接口
 * @author lgx
 * 2016-10-13
 */
public interface RoleDaoI extends BaseDaoI<Role> {

	/**
	 * 分页查询角色信息
	 * @return
	 */
	public Pager<Role> dataGrid();
	
	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	//public Role get(int id);
}
