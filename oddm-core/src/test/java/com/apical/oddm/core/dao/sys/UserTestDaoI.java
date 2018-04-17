package com.apical.oddm.core.dao.sys;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.UserTest;

/**
 * 用户表Dao接口
 * @author lgx
 * 2016-10-13
 */
public interface UserTestDaoI extends BaseDaoI<UserTest> {

	/**
	 * 登录验证查询单个用户
	 * @param user 仅支持用（登录名）或（登录名和密码）等查询
	 * @return
	 */
	public UserTest login(UserTest user);
	
	/**
	 * 分页查询用户信息
	 * @return
	 */
	public Pager<UserTest> dataGrid();

	/**
	 * 按条件查询用户列表
	 * @param user
	 * @return
	 */
	List<UserTest> listUsers(UserTest user);
}
