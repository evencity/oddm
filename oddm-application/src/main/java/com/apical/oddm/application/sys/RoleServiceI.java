package com.apical.oddm.application.sys;

import java.io.Serializable;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.core.model.sys.Role;
import com.apical.oddm.core.model.sys.User;

/**
 * 角色表操作接口
 * @author lgx
 * 2016-10-14
 */
public interface RoleServiceI extends BaseServiceI<Role> {

	/**
	 * 分页获取角色
	 * @return
	 */
	public Pager<Role> dataGrid();

	/**
	 * 增加
	 * @param role
	 * @return 主键
	 */
	public Serializable add(Role role);

	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id);

	/**
	 * 修改
	 * @param role
	 */
	public void edit(Role role);

	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	public Role get(int id);

	/**
	 * 获取角色对应的用户
	 * @param roleId 角色id
	 * @return
	 */
	public Set<User> getRoleUser(int roleId);
	
	/**
	 * 获取角色对应的用户
	 * @param roleName 角色名
	 * @return
	 */
//	public Set<User> getUserByRoleName(String roleName);
	
	/**
	 * 获取角色对应的资源
	 * @param roleId 角色id
	 * @return
	 */
	public Set<Resource> getRoleResource(int roleId);
	
	/**
	 * 授权
	 * @param roleId 角色id
	 * @param resourceIds 资源id集合
	 */
	public void grant(int roleId, Set<Integer> resourceIds);
}
