package com.apical.oddm.core.dao.sys;

import java.util.List;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.sys.Resource;

/**
 * 资源表Dao接口
 * @author lgx
 * 2016-10-13
 */
public interface ResourceDaoI extends BaseDaoI<Resource> {

	/**
	 * 查询全部资源
	 * @return
	 */
	public List<Resource> treeGrid();
	
	/**
	 * 通过类型获取所有资源列表
	 * @return
	 */
	public List<Resource> treeGrid(Set<Integer> type);
	
	/**
	 * 通过用户表id获取用户拥有的资源id
	 * @param userId 用户表id
	 * @return
	 */
	public List<Resource> getUserResource(int userId);

	/**
	 * 更新或者插入
	 * @param resource
	 */
	public void saveOrUpdate(Resource resource);

	/**
	 * 通过批量资源id获取批量资源列表
	 * @param resourceIds
	 * @return
	 */
	public List<Resource> listGid(Set<Integer> resourceIds);
}
