package com.apical.oddm.core.dao.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDaoI {

	@Override
	public Pager<User> dataGrid() {
		String hql = "select distinct t from User t left join fetch t.organization o left join fetch t.roles r order by t.id";
		/*Pager<User> find = this.find(hql);
		String hqlCount = "select count(*) from User";
		find.setTotal((long)this.queryObject(hqlCount));
		return find;*/
		return this.findSpecial(hql);
	}
	
	@Override
	public User get(int id, boolean lazy) {
		if (lazy) {
			this.get(id);
		}
		String hql = "select distinct t from User t left join fetch t.organization left join fetch t.roles r where t.id=?";
		return (User) this.queryObject(hql, id);
	}

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select distinct t from User t left join fetch t.organization left join fetch t.roles r where 1=1";
		if (user.getLoginname() != null) {
			hql +=" and t.loginname=:loginname";
			params.put("loginname", user.getLoginname());
			if (user.getPassword() != null) {
				hql +=" and t.password=:password";
				params.put("password", user.getPassword());
			}
		}
		return (User) this.queryObjectByAlias(hql, params);
	}
	
	@Override
	public Pager<User> dataGridByUsers(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		//如果单独获取organization的id，则不用 join fetch t.organization
		//String hql = "select distinct t from User t left join fetch t.organization o left join fetch t.roles r where 1=1";
		String hql = "select distinct t from User t left join fetch t.organization o left join fetch t.roles r where 1=1";
		if (user.getLoginname() != null) {
			hql +=" and t.loginname like :loginname";
			params.put("loginname", "%"+user.getLoginname()+"%");
		}
		if (user.getUsername() != null) {
			hql +=" and t.username like :username";
			params.put("username", "%"+user.getUsername()+"%");
		}
		if (user.getUsercode() != null) {
			hql +=" and t.usercode like :usercode";
			params.put("usercode", "%"+user.getUsercode()+"%");
		}
		if (user.getState() != null) {
			hql +=" and t.state=:state";
			params.put("state", user.getState());
		}
		//System.out.println(hql+" "+user.getUsername());
		//System.out.println("hqlhqlhqlhqlhqlhqlhqlhql "+hql);
		return this.findByAlias(hql, params);
	}

	@Override
	public List<User> getUserListByOrganization(int orgId) {
		String hql = "select distinct t from User t left join fetch t.organization o left join fetch t.roles r where o.id=?";
		return this.list(hql, orgId);
	}

	@Override
	public User getByLoginName(String loginname) {
		String hql = "select distinct t from User t left join fetch t.organization o left join fetch t.roles r where t.loginname=?";
		return (User) this.queryObject(hql, loginname);
	}

	@Override
	public User getByUsName(String username, boolean lazy) {
		String hql = null;
		if (lazy) {
			hql = "select distinct t from User t where t.username=?";
		} else {
			hql = "select distinct t from User t left join fetch t.organization o left join fetch t.roles r where t.username=?";
		}
		return (User) this.queryObject(hql, username);
	}

	@Override
	public List<User> listByUsername(Set<String> roleNames, String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		if (roleNames == null || roleNames.isEmpty()) {
			String hql = "select distinct t from User t where t.username like ?";
			return this.list(hql, "%"+username+"%");
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			String hql = "select distinct t from User t join fetch t.roles r where 1=1 and (";
			int i = 1;
			for (String role : roleNames) {
				hql +="r.name like :name"+i+" or ";
				params.put("name"+i, "%"+role+"%");
				i++;
			}
			hql = hql.substring(0, hql.lastIndexOf("or")-1);
			hql +=") and t.username like :username";
			params.put("username", "%"+username+"%");
			return this.listByAlias(hql, params);
		}
	}

	@Override
	public List<User> listUsersWithOrderResource(Set<String> resouces) {
		if (resouces == null || resouces.isEmpty()) {
			String hql = "select distinct t from User t";
			return this.list(hql);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			String hql = "select distinct t from User t join fetch t.roles rs join fetch rs.resources r where 1=1 and (";
			int i = 1;
			for (String resouce : resouces) {
				hql +="r.name=:name"+i+" or ";
				params.put("name"+i, resouce);
				i++;
			}
			hql = hql.substring(0, hql.lastIndexOf("or")-1);
			hql +=")";
			return this.listByAlias(hql, params);
		}
	}
}
