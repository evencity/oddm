package com.apical.oddm.facade.sys.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.apical.oddm.application.sys.RoleServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.core.model.sys.Role;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.base.dto.Tree;
import com.apical.oddm.facade.sys.RoleFacade;
import com.apical.oddm.facade.sys.assembler.RoleAssembler;
import com.apical.oddm.facade.sys.command.RoleCommand;
import com.apical.oddm.facade.sys.dto.RoleDTO;
import com.apical.oddm.facade.sys.dto.UserDTO;

@Component("roleInfoFacade")
public class RoleFacadeImpl implements RoleFacade {

	@Autowired
	private RoleServiceI roleService;

	@Override
	public BasePage<RoleDTO> dataGrid() {
		// TODO Auto-generated method stub
		return RoleAssembler.toRolesPageDTO(roleService.dataGrid());
	}

	@Override
	public List<Tree> roleList() {
		// TODO Auto-generated method stub
		Pager<Role> dataGrid = roleService.dataGrid();
		List<Tree> list = new ArrayList<Tree>();
		if (dataGrid != null && dataGrid.getRows().size() > 0) {
			for (Role role : dataGrid.getRows()) {
				Tree tree = new Tree();
				tree.setId(role.getId() + "");
				tree.setText(role.getName());
				list.add(tree);
			}
		}
		return list;
	}

	@Override
	public Boolean add(RoleCommand roleCommand) {
		// TODO Auto-generated method stub、
		if (roleCommand != null) {
			Serializable id = roleService.add(RoleAssembler.toRole(roleCommand));
			if (id != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			roleService.delete(id);
		}
	}

	@Override
	public void edit(RoleCommand roleCommand) {
		// TODO Auto-generated method stub
		if (roleCommand != null) {
			Role role = roleService.get(roleCommand.getId());
			role.setDescription(roleCommand.getDescription());
			role.setName(roleCommand.getName());
			roleService.edit(role);
			;
		}

	}

	@Override
	public RoleDTO get(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			Role role = roleService.get(id);
			if (role != null) {
				RoleDTO roleDTO = new RoleDTO();
				roleDTO.setDescription(role.getDescription());
				roleDTO.setId(role.getId());
				roleDTO.setName(role.getName());
				return roleDTO;
			}
		}
		return null;
	}

	@Override
	public Set<UserDTO> getRoleUser(Integer roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleDTO getRoleResource(Integer roleId) {
		// TODO Auto-generated method stub
		RoleDTO roleDTO = new RoleDTO();
		if (roleId != null) {
			Set<Resource> roleResource = roleService.getRoleResource(roleId);

			roleDTO.setId(roleId);
			roleDTO.setName(roleService.get(roleId).getName());
			String ids = "";
			String names = "";
			if (roleResource != null && roleResource.size() > 0) {
				for (Resource resource : roleResource) {
					ids += resource.getId() + ",";
					names += resource.getName() + ",";
				}
				if (names.length() > 0) {
					roleDTO.setResourceIdNames(names.substring(0, names.length() - 1));
					roleDTO.setResourceIds(ids.substring(0, ids.length() - 1));
				}
			}

		}
		return roleDTO;
	}

	@Override
	public void grant(Integer roleId, String resourceIds) {
		// TODO Auto-generated method stub
		if (roleId != null && resourceIds != null) {
			Set<Integer> set = new HashSet<Integer>();
			if (!StringUtils.isEmpty(resourceIds)) {
				String[] s = resourceIds.split(",");
				for (String ss : s) {
					set.add(Integer.parseInt(ss));
				}
			}
			roleService.grant(roleId, set);
		}
	}
}
