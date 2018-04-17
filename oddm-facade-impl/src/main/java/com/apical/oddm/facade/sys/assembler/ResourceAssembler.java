package com.apical.oddm.facade.sys.assembler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.facade.sys.command.ResourceCommand;
import com.apical.oddm.facade.sys.dto.ResourceDTO;

public class ResourceAssembler {

	public static Resource toResource(ResourceCommand resourceCommand) {
		if (resourceCommand == null) {
			return null;
		}
		Resource resource = new Resource();
		resource.setIcon(resourceCommand.getIcon());
		resource.setResourcetype(resourceCommand.getResourcetype());
		resource.setUrl(resourceCommand.getUrl());
		resource.setName(resourceCommand.getName());
		if (resourceCommand.getPid() != null) {

		}
		// System.out.println(resource.getName());
		return resource;

	}

	public static ResourceDTO toResourceDTO(Resource resource) {
		if (resource == null) {
			return null;
		}
		ResourceDTO resourceDTO = new ResourceDTO();
		resourceDTO.setCreatedatetime(resource.getCreatedatetime() + "");
		resource.setDescription(resource.getDescription());
		resourceDTO.setIcon(resource.getIcon());
		resourceDTO.setIconCls(resource.getIcon());
		resourceDTO.setId(resource.getId());
		resourceDTO.setText(resource.getName());
		resourceDTO.setName(resource.getName());
		if (resource.getResource() != null) {
			resourceDTO.setPid(resource.getResource().getId());
			// resourceDTO.setPname(resource.getResource().getName());
		} else {
			resourceDTO.setPid(null);
			// resourceDTO.setPname(null);
		}
		resourceDTO.setResourcetype(resource.getResourcetype());
		resourceDTO.setState(resource.getState());
		resourceDTO.setUrl(resource.getUrl());
		return resourceDTO;
	}

	public static Set<ResourceDTO> toResourcesInfoDTO(Set<Resource> resources) {
		Set<ResourceDTO> dtos = new HashSet<ResourceDTO>();
		if (resources == null) {
			return dtos;
		}
		for (Resource resource : resources) {
			ResourceDTO dto = toResourceDTO(resource);
			if (dto != null) {
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public static List<ResourceDTO> toResourcesInfoDTO(List<Resource> resources) {
		List<ResourceDTO> dtos = new ArrayList<ResourceDTO>();
		if (resources == null) {
			return dtos;
		}
		for (Resource resource : resources) {
			ResourceDTO dto = toResourceDTO(resource);
			if (dto != null) {
				dtos.add(dto);
			}
		}
		return dtos;
	}
}
