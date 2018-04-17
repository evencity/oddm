package com.apical.oddm.facade.sys.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Role;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.sys.command.UserCommand;
import com.apical.oddm.facade.sys.command.UserLoginCommand;
import com.apical.oddm.facade.sys.dto.UserDTO;
import com.apical.oddm.facade.util.TimeUtil;

@Component
public class UserAssembler {

	public static void main(String[] args) {

	}

	public static UserDTO toUserDTO(User user, boolean lazy) {
		if (user == null) {
			return null;
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setAge(user.getAge());
		userDTO.setCreatedatetime(TimeUtil.dateToStringDetaile(user.getCreatedatetime()));
		userDTO.setLoginname(user.getLoginname());
		userDTO.setDescription(user.getDescription());
		// userDTO.setPassword(user.getPassword()); //不能返回到页面，修复bug
		userDTO.setSex(user.getSex());
		userDTO.setState(user.getState());
		userDTO.setUsercode(user.getUsercode());
		if (user.getOrganization() != null) {
			userDTO.setOrganizationId(user.getOrganization().getId());
			userDTO.setOrganizationName(user.getOrganization().getName());
		}
		if (lazy) {
			if (user.getRoles().size() > 0) {
				String ids = "";
				String roleNames = "";
				for (Role role : user.getRoles()) {
					ids += role.getId() + ",";
					roleNames += role.getName() + ",";
				}
				userDTO.setRoleIds(ids.substring(0, ids.length() - 1));
				userDTO.setRoleNames(roleNames.substring(0, roleNames.length() - 1));
			}
		}

		return userDTO;
	}

	public static List<UserDTO> toUsersDTOs(List<User> users, boolean lazy) {
		if (users == null) {
			return null;
		}
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for (User u : users) {
			userDTOs.add(UserAssembler.toUserDTO(u, lazy));
		}
		return userDTOs;
	}

	public static User toUser(UserLoginCommand userLoginCommand) {
		if (userLoginCommand == null) {
			return null;
		}
		User user = new User();
		user.setLoginname(userLoginCommand.getLoginname());
		user.setPassword(userLoginCommand.getPassword());
		return user;
	}

	public static User toUser(UserCommand userCommand) {
		if (userCommand == null) {
			return null;
		}
		User user = new User();
		user.setLoginname(userCommand.getLoginname());
		user.setPassword(userCommand.getPassword());
		user.setAge(TimeUtil.stringToDate(userCommand.getAge()));
		user.setUsername(userCommand.getUsername());
		return user;
	}

	/*
	 * public static List<UserInfoDTO> toUserInfosDTONOTPassword(List<User> users ) { List<UserInfoDTO> dtos = new ArrayList<UserInfoDTO>(); if(users == null){ return dtos; }
	 * for(User user : users){ UserInfoDTO dto = toUserInfoDTONOTPassword(user); if(dto != null){ dtos.add(dto); } } return dtos; }
	 * 
	 * public static List<UserDeptListInfo> toUserDeptsDTO(List<Organization> treeGrid) { // TODO Auto-generated method stub List<UserDeptListInfo> list = new ArrayList<>();
	 * 
	 * for (Organization o : treeGrid) { if (o.getOrganization() != null) { UserDeptListInfo userDeptListInfo = new UserDeptListInfo();
	 * userDeptListInfo.setId(o.getId().toString()); userDeptListInfo.setName(o.getName()); userDeptListInfo.setParentId(o.getOrganization().getId()); list.add(userDeptListInfo);
	 * for (User u : o.getUsers()) { UserDeptListInfo userDeptListInfo1 = new UserDeptListInfo(); userDeptListInfo1.setId("user_"+u.getId().toString());
	 * userDeptListInfo1.setName(u.getLoginname()); userDeptListInfo1.setParentId(u.getOrganization().getId()); list.add(userDeptListInfo1); } } else { UserDeptListInfo
	 * userDeptListInfo2 = new UserDeptListInfo(); userDeptListInfo2.setId(o.getId().toString()); userDeptListInfo2.setName(o.getName()); userDeptListInfo2.setParentId(0);
	 * list.add(userDeptListInfo2); }
	 * 
	 * 
	 * } return list; }
	 */
	public static BasePage<UserDTO> toUserInfoPageDTO(Pager<User> pager, boolean lazy) {
		if (pager != null) {
			BasePage<UserDTO> basePage = new BasePage<UserDTO>();
			basePage.setRows(UserAssembler.toUsersDTOs(pager.getRows(), lazy));
			basePage.setTotal(pager.getTotal());
			return basePage;
		}
		return null;
	}
}
