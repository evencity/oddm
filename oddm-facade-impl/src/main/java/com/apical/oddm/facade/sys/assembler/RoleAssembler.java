package com.apical.oddm.facade.sys.assembler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.core.model.sys.Role;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.sys.command.RoleCommand;
import com.apical.oddm.facade.sys.dto.RoleDTO;

public class RoleAssembler {

	public static Role toRole(RoleCommand roleCommand) {
		if (roleCommand != null) {
			Role role = new Role();
			/* role.setId(roleCommand.getId()); */
			role.setDescription(roleCommand.getDescription());
			role.setName(roleCommand.getName());
			return role;
		}
		return null;
	}

	public static RoleDTO toRoleDTO(Role role) {
		if (role != null) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setDescription(role.getDescription());
			roleDTO.setId(role.getId());
			roleDTO.setName(role.getName());
			if (role.getResources() != null) {
				if (role.getResources().size() > 0) {
					Set<Resource> set = role.getResources();
					String ids = "";
					String names = "";
					for (Resource resource : set) {
						ids += resource.getId() + ",";
						names += resource.getName() + ",";
					}
					roleDTO.setResourceIdNames(names.substring(0, names.length() - 1));
					roleDTO.setResourceIds(ids.substring(0, ids.length() - 1));
				}
			}

			return roleDTO;
		}
		return null;
	}

	public static Set<RoleDTO> toRoleDTO(Set<Role> roles) {
		if (roles.size() > 0) {
			Set<RoleDTO> RoleDTOs = new HashSet<>();
			for (Role role : roles) {
				RoleDTOs.add(toRoleDTO(role));
			}
			return RoleDTOs;
		}
		return null;
	}

	public static List<RoleDTO> toRolesDTO(List<Role> roles) {
		List<RoleDTO> dtos = new ArrayList<RoleDTO>();
		if (roles == null) {
			return dtos;
		}
		for (Role role : roles) {
			RoleDTO dto = toRoleDTO(role);
			if (dto != null) {
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public static BasePage<RoleDTO> toRolesPageDTO(Pager<Role> pager) {
		BasePage<RoleDTO> basePage = new BasePage<RoleDTO>();
		if (pager == null) {
			return basePage;
		}
		List<RoleDTO> list = new ArrayList<RoleDTO>();
		for (Role role : pager.getRows()) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setDescription(role.getDescription());
			roleDTO.setId(role.getId());
			roleDTO.setName(role.getName());
			list.add(roleDTO);
		}
		basePage.setRows(list);
		basePage.setTotal(pager.getTotal());
		return basePage;
	}

}
