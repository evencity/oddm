package com.apical.oddm.core.dao.sys;

import java.util.List;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.User;

/**
 * 用户表Dao接口
 * @author lgx
 * 2016-10-13
 */
public interface UserDaoI extends BaseDaoI<User> {

	/**
	 * 登录验证查询单个用户
	 * @param user 仅支持用（登录名）或（登录名和密码）等查询
	 * @return
	 */
	public User login(User user);
	
	/**
	 * 查单个用户信息
	 * @param id
	 * @param lazy true慢加载，false快加载
	 * @return 快加载
	 */
	public User get(int id, boolean lazy);
	
	/**
	 * 查询单个用户
	 * @param loginname 传入登录名
	 * @return 快加载
	 */
	public User getByLoginName(String loginname);
	
	/**
	 * 查询单个用户
	 * @param username
	 * @param lazy true为慢加载， false为快加载
	 * @return
	 */
	public User getByUsName(String username, boolean lazy);
	
	/**
	 * @return 返回所有具有订单查看权限的用户信息
	 */
	public List<User> listUsersWithOrderResource(Set<String> resouces);
	
	/**
	 * 分页查询用户信息
	 * @return
	 */
	public Pager<User> dataGrid();

	/**
	 * 按条件查询用户列表
	 * @param user
	 * @return
	 */
	public Pager<User> dataGridByUsers(User user);

	/**
	 * 通过机构id获取所用用户
	 * @param orgId
	 * @return
	 */
	public List<User> getUserListByOrganization(int orgId);
	
	/**
	 * 通过用户名查询用户信息，半模糊查询 username%
	 * @param username
	 * @return
	 */
	public List<User> listByUsername(Set<String> roleNames, String username);
}
