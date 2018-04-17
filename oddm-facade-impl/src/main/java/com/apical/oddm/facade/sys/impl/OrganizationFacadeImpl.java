package com.apical.oddm.facade.sys.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.sys.OrganizationServiceI;
import com.apical.oddm.core.model.sys.Organization;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.sys.OrganizationFacade;
import com.apical.oddm.facade.sys.assembler.OrganizationAssembler;
import com.apical.oddm.facade.sys.command.OrganizationCommand;
import com.apical.oddm.facade.sys.dto.OrganizationDTO;
import com.apical.oddm.facade.sys.dto.UserDeptDTO;

@Component("organizationInfoFacade")
public class OrganizationFacadeImpl implements OrganizationFacade {

	@Autowired
	private OrganizationServiceI organizationService;

	@Override
	public List<OrganizationDTO> treeGrid() {
		// TODO Auto-generated method stub
		return OrganizationAssembler.toOrganizationInfosDTO(organizationService.treeGrid());
	}

	@Override
	public Boolean add(OrganizationCommand organizationCommand) {
		// TODO Auto-generated method stub
		if (organizationCommand != null) {
			Organization organization = new Organization();
			organization.setIcon(organizationCommand.getIcon());
			organization.setSeq(organizationCommand.getSeq());
			organization.setName(organizationCommand.getName());
			if (organizationCommand.getPid() != null) {
				Organization organization2 = organizationService.get(organizationCommand.getPid());
				organization.setOrganization(organization2);
			}
			Serializable id = organizationService.add(organization);
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

	}

	@Override
	public void edit(OrganizationCommand organizationCommand) {
		// TODO Auto-generated method stub
		if (organizationCommand != null) {
			Organization organization = organizationService.get(organizationCommand.getId());
			organization.setIcon(organizationCommand.getIcon());
			organization.setName(organizationCommand.getName());
			organization.setSeq(organizationCommand.getSeq());
			// System.out.println(organizationCommand);
			if (organizationCommand.getPid() != null) {
				Organization organization2 = new Organization(organizationCommand.getPid());
				organization.setOrganization(organization2);
			} else {
				organization.setOrganization(null);
			}
			organizationService.edit(organization);
		}
	}

	@Override
	public OrganizationDTO get(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			return OrganizationAssembler.toOrganizationDTO(organizationService.get(id));
		}
		return null;
	}

	@Override
	public List<OrganizationDTO> companyList() {
		// TODO Auto-generated method stub
		return OrganizationAssembler.toCompany(organizationService.treeGrid());
	}

	@Override
	public List<UserDeptDTO> usersList() {
		// TODO Auto-generated method stub
		List<UserDeptDTO> list = new ArrayList<>();
		if (organizationService.treeGrid() != null && organizationService.treeGrid().size() > 0) {
			for (Organization o : organizationService.treeGrid()) {
				if (o.getOrganization() != null) {
					UserDeptDTO userDeptDTO = new UserDeptDTO();
					// System.out.println(o.getName()+"|"+o.getOrganization().getName());
					userDeptDTO.setId(o.getId().toString() + "");
					userDeptDTO.setText(o.getName() + "");
					userDeptDTO.setPid(o.getOrganization().getId() + "");

					list.add(userDeptDTO);
					for (User u : o.getUsers()) {
						// System.out.println("\t"+u.getLoginname()+"\t"+u.getOrganization().getId());
						UserDeptDTO userDeptDTO1 = new UserDeptDTO();
						userDeptDTO1.setId("user_" + u.getId().toString());
						userDeptDTO1.setText(u.getLoginname());
						userDeptDTO1.setPid(u.getOrganization().getId() + "");
						userDeptDTO.setIconCls(o.getOrganization().getIcon());
						list.add(userDeptDTO1);
					}
				} else {
					// System.out.println(o.getName()+"|"+o.getOrganization());
					UserDeptDTO userDeptDTO = new UserDeptDTO();
					userDeptDTO.setId(o.getId().toString());
					userDeptDTO.setText(o.getName());
					userDeptDTO.setPid(null);
					userDeptDTO.setIconCls("icon-home");
					list.add(userDeptDTO);
				}
			}
		}

		return list;
	}

}
