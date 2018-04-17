package com.apical.oddm.facade.sys;

import java.util.List;
import java.util.Set;

import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.base.dto.Tree;
import com.apical.oddm.facade.sys.command.RoleCommand;
import com.apical.oddm.facade.sys.dto.RoleDTO;
import com.apical.oddm.facade.sys.dto.UserDTO;



public interface RoleFacade {

	/**
	 * 分页获取角色
	 * @return
	 */
	public BasePage<RoleDTO> dataGrid();
	
	/**
	 * 树形显示
	 * @return
	 */
	public List<Tree> roleList();
	/**
	 * 增加
	 * @param role
	 * @return
	 */
	public Boolean add(RoleCommand role);

	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 修改
	 * @param role
	 */
	public void edit(RoleCommand role);

	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	public RoleDTO get(Integer id);

	/**
	 * 获取角色对应的用户
	 * @param roleId 角色id
	 * @return
	 */
	public Set<UserDTO> getRoleUser(Integer roleId);
	
	/**
	 * 获取角色对应的资源
	 * @param roleId 角色id
	 * @return
	 */
	public RoleDTO getRoleResource(Integer roleId);
	
	/**
	 * 授权
	 * @param roleId 角色id
	 * @param resourceIds 资源id集合
	 */
	public void grant(Integer roleId, String resourceIds);
}
