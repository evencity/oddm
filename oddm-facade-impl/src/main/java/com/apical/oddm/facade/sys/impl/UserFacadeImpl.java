package com.apical.oddm.facade.sys.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.sys.OrganizationServiceI;
import com.apical.oddm.application.sys.RoleServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sys.Organization;
import com.apical.oddm.core.model.sys.Role;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.sys.UserFacade;
import com.apical.oddm.facade.sys.assembler.ResourceAssembler;
import com.apical.oddm.facade.sys.assembler.UserAssembler;
import com.apical.oddm.facade.sys.command.UserCommand;
import com.apical.oddm.facade.sys.command.UserLoginCommand;
import com.apical.oddm.facade.sys.dto.ResourceDTO;
import com.apical.oddm.facade.sys.dto.UserDTO;
import com.apical.oddm.facade.util.StringUtil;
import com.apical.oddm.facade.util.TimeUtil;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Component("userInfoFacade")
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserServiceI userService;
	@Autowired
	private OrganizationServiceI organizationService;
	@Autowired
	private RoleServiceI roleService;

	@Override
	public UserDTO login(UserLoginCommand userLoginCommand) {
		// TODO Auto-generated method stub
		return UserAssembler.toUserDTO(userService.login(UserAssembler.toUser(userLoginCommand)), false);
	}

	@Override
	public List<ResourceDTO> getUserResource(int userId) {
		// TODO Auto-generated method stub
		return ResourceAssembler.toResourcesInfoDTO(userService.getUserResource(userId));
	}

	@Override
	public BasePage<UserDTO> dataGrid(UserCommand userCommand, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if ("organizationName".equals(pageCommand.getSort())) {
			SystemContext.setSort("o.name");
		} else {
			if (StringUtils.isNotBlank(pageCommand.getSort()))
				SystemContext.setSort("t." + pageCommand.getSort());
		}
		if ("asc".equals(pageCommand.getOrder())) {
			SystemContext.setOrder("desc");
		} else {
			SystemContext.setOrder("asc");
		}
		if (userCommand.getUsername() == null || "".equals(userCommand.getUsername().trim())) {
			Pager<User> dataGrid = userService.dataGrid();
			return UserAssembler.toUserInfoPageDTO(dataGrid, true);
		} else {
			Pager<User> byUsername = userService.getByUsername(userCommand.getUsername());
			// System.out.println(byUsername.getTotal());
			return UserAssembler.toUserInfoPageDTO(byUsername, true);
		}
	}

	@Override
	public Boolean add(UserCommand userCommand) {
		// TODO Auto-generated method stub
		if (userCommand == null) {
			return null;
		}
		User user = new User();
		user.setLoginname(userCommand.getLoginname());
		user.setPassword(userCommand.getPassword());
		user.setAge(TimeUtil.stringToDate(userCommand.getAge()));
		user.setUsername(userCommand.getUsername());
		user.setSex(userCommand.getSex());
		user.setOrganization(new Organization(userCommand.getOrganizationId()));
		Set<Integer> ids = StringUtil.stringToSet(userCommand.getRoleIds());
		Set<Role> roles = new HashSet<Role>();
		for (Integer id : ids) {
			Role role = roleService.get(id);
			roles.add(role);
		}
		user.setRoles(roles);
		Serializable id = userService.add(user);
		if (id != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void delete(Integer id) {
		if (id != null) {
			userService.delete(id);
		}
	}

	@Override
	public void edit(UserCommand userCommand) {
		// TODO Auto-generated method stub
		if (userCommand != null) {
			User user = userService.get(userCommand.getId());
			user.setLoginname(userCommand.getLoginname());
			user.setAge(TimeUtil.stringToDate(userCommand.getAge()));
			user.setUsername(userCommand.getUsername());
			user.setState(userCommand.getState());
			user.setSex(userCommand.getSex());
			if (userCommand.getOrganizationId() != null) {
				user.setOrganization(new Organization(userCommand.getOrganizationId()));
			}
			if (StringUtils.isNotBlank(userCommand.getRoleIds())) {
				Set<Integer> ids = StringUtil.stringToSet(userCommand.getRoleIds());
				Set<Role> roles = new HashSet<Role>();
				for (Integer id : ids) {
					Role role = roleService.get(id);
					roles.add(role);
				}
				user.setRoles(roles);
			} else {// 删除角色
				user.setRoles(null);
			}
			userService.edit(user);
		}

	}

	@Override
	public UserDTO get(Integer id) {
		// TODO Auto-generated method stub
		UserDTO userDTO = new UserDTO();
		if (id != null) {
			User user = userService.get(id, false);

			if (user != null) {
				BeanUtils.copyProperties(user, userDTO);
				userDTO.setPassword("");
				if (user.getRoles() != null && user.getRoles().size() > 0) {
					String ids = "";
					String roleNames = "";
					for (Role role : user.getRoles()) {
						ids += role.getId() + ",";
						roleNames += role.getName() + ",";
					}
					userDTO.setRoleIds(ids.substring(0, ids.length() - 1));
					userDTO.setRoleNames(roleNames.substring(0, roleNames.length() - 1));
				}
				if (user.getOrganization() != null) {

					userDTO.setOrganizationId(user.getOrganization().getId());
					// userDTO.setOrganizationName(user.getOrganization().getName());
				}
			}
		}
		return userDTO;
	}

	@Override
	public boolean editUserPwd(Integer userId, String oldPwd, String pwd) {
		return userService.editUserPwd(userId, oldPwd, pwd);
	}

	@Override
	public boolean updatePassword(Integer userId) {
		return userService.updatePassword(userId);
	}

	@Override
	public UserDTO getByLoginName(String loginname) {
		return null;
	}

	@Override
	public List<UserDTO> getUserListByOrganization(Integer orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> listByUsername(String username) {
		// TODO Auto-generated method stub
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		if (username != null) {
			List<User> list = userService.listByUsername(username);

			if (list != null && list.size() > 0) {
				for (User user : list) {
					UserDTO userDTO = new UserDTO();
					userDTO.setUsername(user.getUsername());
					userDTO.setId(user.getId());
					userDTOs.add(userDTO);
				}
			}
		}
		return userDTOs;
	}

	@Override
	public List<UserDTO> listOrderAuditor(String username) {
		// TODO Auto-generated method stub
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		if (username != null) {
			List<User> list = userService.listOrderAuditor(username);
			if (list != null && list.size() > 0) {
				for (User user : list) {
					UserDTO userDTO = new UserDTO();
					userDTO.setUsername(user.getUsername());
					userDTO.setId(user.getId());
					userDTOs.add(userDTO);
				}
			}
		}

		return userDTOs;
	}

	@Override
	public Boolean getByUsName(String username) {
		// TODO Auto-generated method stub
		if (username != null) {
			User byUsName = userService.getByUsName(username, true);
			if (byUsName != null) {
				return true;
			}
			return false;
		}

		return false;
	}

	@Override
	public List<UserDTO> listSeller(String username) {
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		if (username != null) {
			List<User> list = userService.listSeller(username);
			if (list != null && list.size() > 0) {
				for (User user : list) {
					UserDTO userDTO = new UserDTO();
					userDTO.setUsername(user.getUsername());
					userDTO.setId(user.getId());
					userDTOs.add(userDTO);
				}
			}
		}

		return userDTOs;
	}

	@Override
	public List<UserDTO> listMerchandiser(String username) {
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		if (username != null) {
			List<User> list = userService.listMerchandiser(username);
			if (list != null && list.size() > 0) {
				for (User user : list) {
					UserDTO userDTO = new UserDTO();
					userDTO.setUsername(user.getUsername());
					userDTO.setId(user.getId());
					userDTOs.add(userDTO);
				}
			}
		}

		return userDTOs;
	}

	@Override
	public boolean editUserPwdForAdmin(Integer userId, String password) {
		if(userId != null && StringUtils.isNotBlank(password) ){
			userService.editUserPwdForAdmin(userId,password);
			return true;
		}
		return false;
	}
}
