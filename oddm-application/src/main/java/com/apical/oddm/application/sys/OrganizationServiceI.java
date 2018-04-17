package com.apical.oddm.application.sys;

import java.io.Serializable;
import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Organization;

/**
 * 机构表相关操作接口
 * @author lgx
 * 2016-10-12
 */
public interface OrganizationServiceI extends BaseServiceI<Organization> {

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
	
	/**
	 * 添加机构
	 * @param organization
	 * @return 主键
	 */
	public Serializable add(Organization organization);

	/**
	 * 删除机构
	 * @param id 机构表id
	 */
	public void delete(int id);

	/**
	 * 更新机构信息
	 * @param organization
	 */
	public void edit(Organization organization);

	/**
	 * 查询指定机构
	 * @param id 机构表id
	 * @return
	 */
	public Organization get(int id);
}
