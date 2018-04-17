package com.apical.oddm.facade.sys.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.sys.ResourceServiceI;
import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.core.model.sys.SysConfig;
import com.apical.oddm.facade.sys.ResourceFacade;
import com.apical.oddm.facade.sys.assembler.ResourceAssembler;
import com.apical.oddm.facade.sys.command.ResourceCommand;
import com.apical.oddm.facade.sys.dto.ResourceDTO;

@Component("resourceInfoFacade")
public class ResourceFacadeImpl implements ResourceFacade {

	@Autowired
	private ResourceServiceI resourceService;

	@Autowired
	private SysConfigServiceI sysConfigService;

	@Override
	public List<ResourceDTO> treeGrid() {
		// TODO Auto-generated method stub
		List<Resource> treeGrid = resourceService.treeGrid();
		List<ResourceDTO> list = new ArrayList<ResourceDTO>();
		if (treeGrid != null && treeGrid.size() > 0) {
			for (Resource resource : treeGrid) {
				ResourceDTO resourceDTO = new ResourceDTO();
				resourceDTO.setCreatedatetime(resource.getCreatedatetime() + "");
				resource.setDescription(resource.getDescription());
				resourceDTO.setIcon(resource.getIcon());
				resourceDTO.setIconCls(resource.getIcon());
				resourceDTO.setId(resource.getId());
				resourceDTO.setText(resource.getName());
				resourceDTO.setName(resource.getName());
				resourceDTO.setSeq(resource.getSeq());
				if (resource.getResource() != null) {
					resourceDTO.setPid(resource.getResource().getId());
					/*
					 * System.out.println(resourceService.get(resource.getResource().getId())+"...............");
					 * resourceDTO.setPname(resourceService.get(resource.getResource().getId()).getName());
					 */
				} else {
					resourceDTO.setPid(null);
					resourceDTO.setPname(null);
				}
				resourceDTO.setResourcetype(resource.getResourcetype());
				resourceDTO.setState(resource.getState());
				resourceDTO.setUrl(resource.getUrl());
				list.add(resourceDTO);
			}
		}

		// System.out.println("aaaaaaaaaaaaaaaaaaa"+list);
		return list;
	}

	@Override
	public void edit(ResourceCommand resourceCommand) {
		if (resourceCommand != null) {
			Resource resource = resourceService.get(resourceCommand.getId());
			resource.setIcon(resourceCommand.getIcon());
			resource.setResourcetype(resourceCommand.getResourcetype());
			resource.setUrl(resourceCommand.getUrl());
			SysConfig sysConfig = sysConfigService.get("order_unread_flag");
			if (sysConfig == null || StringUtils.isBlank(sysConfig.getValue())) {
			} else {
				String[] split = sysConfig.getValue().split("\\|");
				for (String resouce : split) {
					if (resouce.equals(resource.getName()) || resouce.equals(resource.getName())) {
						throw new RuntimeException("系统配置项order_unread_flag关联了本菜单名，先修改系统配置项！");
					}
				}
			}
			resource.setName(resourceCommand.getName());
			resource.setSeq(resourceCommand.getSeq());
			if (resourceCommand.getPid() != null) {
				Resource resource2 = resourceService.get(resourceCommand.getPid());
				resource.setResource(resource2);
			} else {
				resource.setResource(null);
			}
			resourceService.edit(resource);
		}

	}

	@Override
	public ResourceDTO get(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			Resource resource = resourceService.get(id);
			ResourceDTO resourceDTO = new ResourceDTO();
			resourceDTO.setCreatedatetime(resource.getCreatedatetime() + "");
			resource.setDescription(resource.getDescription());
			resourceDTO.setIcon(resource.getIcon());
			resourceDTO.setIconCls(resource.getIcon());
			resourceDTO.setId(resource.getId());
			resourceDTO.setText(resource.getName());
			resourceDTO.setSeq(resource.getSeq());
			resourceDTO.setName(resource.getName());
			if (resource.getResource() != null) {
				resourceDTO.setPid(resource.getResource().getId());
				resourceDTO.setPname(resourceService.get(resource.getResource().getId()).getName());
			} else {
				resourceDTO.setPid(null);
				resourceDTO.setPname(null);
			}
			resourceDTO.setResourcetype(resource.getResourcetype());
			resourceDTO.setState(resource.getState());
			resourceDTO.setUrl(resource.getUrl());
			return resourceDTO;
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void saveOrUpdateName(ResourceCommand resourceCommand) {
		// TODO Auto-generated method stub
		resourceService.saveOrUpdateName(ResourceAssembler.toResource(resourceCommand));
	}

	@Override
	public List<ResourceDTO> menuList() {
		// TODO Auto-generated method stub
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		List<Resource> treeGrid = resourceService.treeGrid(set);
		List<ResourceDTO> list = new ArrayList<ResourceDTO>();
		if (treeGrid != null && treeGrid.size() > 0) {
			for (Resource resource : treeGrid) {
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
					resourceDTO.setPname(resource.getResource().getName());
				} else {
					resourceDTO.setPid(null);
					resourceDTO.setPname(null);
				}
				resourceDTO.setResourcetype(resource.getResourcetype());
				resourceDTO.setState(resource.getState());
				resourceDTO.setUrl(resource.getUrl());
				list.add(resourceDTO);
			}
		}

		return list;
	}

	@Override
	public Boolean add(ResourceCommand resourceCommand) {
		// TODO Auto-generated method stub
		if (resourceCommand != null) {
			Resource resource = new Resource();
			resource.setIcon(resourceCommand.getIcon());
			resource.setResourcetype(resourceCommand.getResourcetype());
			resource.setUrl(resourceCommand.getUrl());
			resource.setName(resourceCommand.getName());
			resource.setSeq(resourceCommand.getSeq());
			if (resourceCommand.getPid() != null) {
				Resource resource2 = resourceService.get(resourceCommand.getPid());
				resource.setResource(resource2);
			}
			Serializable id = resourceService.add(resource);
			if (id != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}
}
