package com.apical.oddm.application.sys;

import java.util.List;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.sys.Resource;

/**
 * 资源操作接口
 * @author lgx
 * 2016-10-13
 */
public interface ResourceServiceI extends BaseServiceI<Resource> {

	/**
	 * 获取所有资源列表
	 * @return
	 */
	public List<Resource> treeGrid();

	/**
	 * 通过类型获取所有资源列表
	 * @return
	 */
	public List<Resource> treeGrid(Set<Integer> type);
	
	//增加资源
	//public Resource add(Resource resource);

	//public void delete(int id);

	/**
	 * 修改资源
	 * @param resource
	 */
	public void edit(Resource resource);

	/**
	 * 获取单个资源
	 * @param id
	 * @return
	 */
	public Resource get(int id);
	
	/**
	 * 测试用，更新资源名称
	 * @param resource
	 */
	@Deprecated
	public void saveOrUpdateName(Resource resource);
}
