package com.apical.oddm.core.dao.sys;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Organization;

/**
 * 机构表Dao接口
 * @author lgx
 * 2016-10-13
 */
public interface OrganizationDaoI extends BaseDaoI<Organization> {

	/**
	 * 获取子节点
	 * @param id
	 * @return
	 */
	public Organization getChildren(int id);
	
	/**
	 * 查询全部机构
	 * @return 返回机构列表
	 */
	public List<Organization> treeGrid();

	/**
	 * 查询全部机构
	 * @return 返回机构列表
	 */
	public List<Organization> treeGrid2();

	/**
	 * 分页查询全部机构
	 * @return 返回机构列表
	 */
	public Pager<Organization> dataGrid();
	
	/*@Deprecated
	public List<OrganizationTest> treeGridTest();
*/
}
