package com.apical.oddm.core.dao.sys;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDaoI {

	@Override
	public Pager<Role> dataGrid() {
		String hql = "from Role t order by t.name";
		return this.find(hql);
	}

	/*@Override
	public Role get(int id) {
		String hql = "from Role t join fetch t.resources r where t.id=?";
		return (Role) this.queryObject(hql, id);
	}*/

	
/*	public Set<User> getRoleUser(int roleId) {
		String hql = "from Role t join fetch t.users where t.id=? order by t.seq";
		Role role = (Role) this.queryObject(hql, roleId);
		if (role != null && role.getUsers() != null) {
			return role.getUsers();
		}
		return null;
	}
	*/
}
