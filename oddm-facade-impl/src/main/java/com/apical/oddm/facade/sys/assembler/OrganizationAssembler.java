package com.apical.oddm.facade.sys.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Organization;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.sys.command.OrganizationCommand;
import com.apical.oddm.facade.sys.dto.OrganizationDTO;

public class OrganizationAssembler {

	public static OrganizationDTO toOrganizationDTO(Organization organization) {

		if (organization != null) {
			OrganizationDTO organizationDTO = new OrganizationDTO();
			BeanUtils.copyProperties(organization, organizationDTO);
			organizationDTO.setIconCls(organization.getIcon());
			organizationDTO.setText(organization.getName());
			if (organization.getOrganization() != null) {
				organizationDTO.setPid(organization.getOrganization().getId());
			}
			return organizationDTO;
		}
		return null;
	}

	public static List<OrganizationDTO> toCompany(List<Organization> organizations) {
		if (organizations != null) {
			List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
			for (Organization organization : organizations) {
				if (organization.getOrganization() == null) {
					list.add(toOrganizationDTO(organization));
				}
			}
			return list;
		}
		return null;
	}

	public static Organization toOrganization(OrganizationCommand organizationCommand) {
		if (organizationCommand != null) {
			Organization organization = new Organization();
			organization.setIcon(organizationCommand.getIcon());
			organization.setName(organizationCommand.getName());
			if (organizationCommand.getPid() != null) {

			}

			return organization;
		}
		return null;
	}

	public static List<OrganizationDTO> toOrganizationInfosDTO(List<Organization> organizations) {
		List<OrganizationDTO> dtos = new ArrayList<OrganizationDTO>();
		if (organizations != null) {
			for (Organization organization : organizations) {
				OrganizationDTO dto = toOrganizationDTO(organization);
				if (dto != null) {
					dtos.add(dto);
				}
			}
			return dtos;
		}
		return dtos;
	}

	public static BasePage<OrganizationDTO> toOrganizationInfoPageDTO(Pager<Organization> pager) {
		if (pager != null) {
			BasePage<OrganizationDTO> basePage = new BasePage<OrganizationDTO>();
			basePage.setRows(OrganizationAssembler.toOrganizationInfosDTO(pager.getRows()));
			basePage.setTotal(pager.getTotal());
			return basePage;
		}
		return null;
	}
}
