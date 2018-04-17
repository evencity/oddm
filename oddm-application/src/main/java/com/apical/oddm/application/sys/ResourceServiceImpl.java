package com.apical.oddm.application.sys;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sys.ResourceDaoI;
import com.apical.oddm.core.exception.OddmRuntimeException;
import com.apical.oddm.core.model.sys.Resource;

@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceServiceI {

	@Autowired
	private ResourceDaoI resourceDao;
	
	@Override
	public List<Resource> treeGrid() {
		return resourceDao.treeGrid();
	}

	@Override
	public void edit(Resource resource) {
		resourceDao.update(resource);
	}

	@Override
	public Resource get(int id) {
		return resourceDao.get(id);
	}

	@Override
	public void saveOrUpdateName(Resource resource) {
		/*测试@DynamicUpdate(true)生效，只能在一个session内生效。
		update sys_resource set name=? where id=?*/
		Resource resourceDatabase = null;
		if (resource.getId() != null) {
			resourceDatabase = resourceDao.get(resource.getId());
		} else {
			throw new OddmRuntimeException("id不能为空");
		}
		if (resourceDatabase != null) {
			resourceDatabase.setName(resource.getName());
		} else {
			resourceDatabase = resource;
		}
		resourceDao.saveOrUpdate(resourceDatabase);
	}

	@Override
	public List<Resource> treeGrid(Set<Integer> type) {
		return resourceDao.treeGrid(type);
	}
}
