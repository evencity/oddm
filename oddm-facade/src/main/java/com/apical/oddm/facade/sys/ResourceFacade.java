package com.apical.oddm.facade.sys;

import java.util.List;

import com.apical.oddm.facade.sys.command.ResourceCommand;
import com.apical.oddm.facade.sys.dto.ResourceDTO;



public interface ResourceFacade {


	/**
	 * 获取所有资源列表
	 * @return
	 */
	public List<ResourceDTO> treeGrid();
	

	/**
	 * 菜单资源
	 * @param resource
	 */
	public List<ResourceDTO> menuList();

	/**
	 * 修改资源
	 * @param resource
	 */
	public void edit(ResourceCommand resource);

	/**
	 * 获取单个资源
	 * @param id
	 * @return
	 */
	public ResourceDTO get(Integer id);
	
	/**
	 * 测试用，更新资源名称
	 * @param resource
	 */
	@Deprecated
	public void saveOrUpdateName(ResourceCommand resource);
	
	//增加资源
	public Boolean add(ResourceCommand resourceCommand);

}
