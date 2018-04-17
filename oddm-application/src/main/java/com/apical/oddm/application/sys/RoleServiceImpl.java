package com.apical.oddm.application.sys;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sys.ResourceDaoI;
import com.apical.oddm.core.dao.sys.RoleDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.core.model.sys.Role;
import com.apical.oddm.core.model.sys.User;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleServiceI {

	@Autowired
	private RoleDaoI roleDao;
	
	@Autowired
	private ResourceDaoI resourceDao;
	
	@Override
	public Pager<Role> dataGrid() {
		return roleDao.dataGrid();
	}

	@Override
	public Serializable add(Role role) {
		return roleDao.add(role);
	}

	@Override
	public void delete(int id) {
		roleDao.delete(id);
	}

	@Override
	public void edit(Role role) {
		roleDao.update(role);
	}

	@Override
	public Role get(int id) {
		return roleDao.get(id);
	}

	@Override
	public void grant(int roleId, Set<Integer> resourceIds) {
		Role role = roleDao.get(roleId);
		Set<Resource> resources = new HashSet<Resource>(0);
		resources.addAll(resourceDao.listGid(resourceIds));
		role.setResources(resources);
	}

	@Override
	public Set<User> getRoleUser(int roleId) {
		Role role = roleDao.get(roleId);
		if (role != null) {
			role.getUsers().isEmpty();//达到快速加载的作用，和from Role t join fetch t.users where t.id=? order by t.seq 都是执行了两条sql
			return role.getUsers();
		} else {
			return null;
		}
	}

	@Override
	public Set<Resource> getRoleResource(int roleId) {
		Role role = roleDao.get(roleId);
		if (role != null) {
			role.getResources().isEmpty();//达到快速加载的作用
			return role.getResources();
		} else {
			return null;
		}
	}
}
