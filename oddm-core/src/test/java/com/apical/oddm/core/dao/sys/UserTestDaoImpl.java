package com.apical.oddm.core.dao.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.UserTest;

@Repository("userTestDao")
public class UserTestDaoImpl extends BaseDaoImpl<UserTest> implements UserTestDaoI {

	@Override
	public UserTest login(UserTest user) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from User t where 1=1";
		if (user.getLoginname() != null) {
			hql +=" and t.loginname = :loginname";
			params.put("loginname", user.getLoginname());
			if (user.getPassword() != null) {
				hql +=" and t.password = :password";
				params.put("loginname", user.getPassword());
			}
		}
		return (UserTest) this.queryObjectByAlias(hql, params);
	}
	
	@Override
	public List<UserTest> listUsers(UserTest user) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from User t where 1=1";
		if (user.getLoginname() != null) {
			hql +=" and t.loginname = :loginname";
			params.put("loginname", user.getLoginname());
		}
		if (user.getUsername() != null) {
			hql +=" and t.username = :username";
			params.put("username", user.getLoginname());
		}
		if (user.getUsercode() != null) {
			hql +=" and t.usercode = :usercode";
			params.put("usercode", user.getUsercode());
		}
		if (user.getState() != null) {
			hql +=" and t.state = :state";
			params.put("state", user.getState());
		}
		return this.list(hql, params);
	}

	@Override
	public Pager<UserTest> dataGrid() {
		String hql = "from User";
		return this.find(hql);
	}
}
