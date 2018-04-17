package com.apical.oddm.application.sys;

import java.io.Serializable;
import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.core.model.sys.User;

/**
 * 用户表操作接口
 * @author lgx
 * 2016-10-12
 */
public interface UserServiceI extends BaseServiceI<User> {

	/**
	 * 分页获取用户信息
	 * @return 立即加载对象
	 */
	public Pager<User> dataGrid();

	/**
	 * @param user
	 * @return
	 */
	//public Pager<User> dataGrid(User user);

	/**
	 * 增加用户
	 * @param user 用户对象
	 * @return 主键
	 */
	public Serializable add(User user);

	/**
	 * 删除用户
	 * @param id 用户表id
	 */
	public void delete(int id);

	/**
	 * @param user
	 */
	public void edit(User user);

	/**
	 * 查单个用户信息
	 * @param id
	 * @param lazy true慢加载，false快加载
	 * @return 快加载
	 */
	public User get(int id, boolean lazy);

	/**
	 * 登录验证
	 * @param user 传入登录名和密码
	 * @return 验证不通过则返回null
	 */
	public User login(User user);

	/**
	 * 修改密码
	 * @param userId 用户id
	 * @param oldPwd 老密码
	 * @param pwd 新密码
	 * @return
	 */
	public boolean editUserPwd(int userId, String oldPwd, String pwd);
	
	/**
	 * 重置用户密码为默认密码
	 * @param userId
	 * @return
	 */
	public boolean updatePassword(int userId);
	
	/**
	 * 重置用户密码为默认密码
	 * @param userId
	 * @return
	 */
	public boolean updateDefaultPassword(int userId);
	
	/**
	 * 通过用户表id获取用户拥有的资源id
	 * @param userId 用户表id
	 * @return
	 */
	public List<Resource> getUserResource(int userId);

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
	 * 通过用户名查询用户信息
	 * @param username
	 * @return
	 */
	@Deprecated
	public Pager<User> getByUsername(String username);

	/**
	 * 根据用户状态查询用户信息
	 * @return
	 */
	@Deprecated
	public Pager<User> getUserListByUserState(int state);
	
	/**
	 * 通过机构id获取所用用户
	 * @param orgId
	 * @return
	 */
	public List<User> getUserListByOrganization(int orgId);
	
	/**
	 * @return 返回所有具有订单查看权限的用户信息
	 */
	public List<User> listUsersWithOrderResource();
	
	/**
	 * @return 返回所有具文档下载页面权限的用户
	 */
	public List<User> listUsersWithDocDownResource();
	
	/**
	 * @return 返回指导书查看权限的用户
	 */
	public List<User> listUsersWithMftResource();
	
	/**
	 * @return 返回Bom查看权限的用户
	 */
	public List<User> listUsersWithBomResource();

	/**
	 * @return 返回具有内部订单查看权限的用户
	 */
	public List<User> listUsersWithSalePoResource();
	
	/**
	 * 通过用户名查询用户信息，模糊查询 
	 * @param username
	 * @return
	 */
	public List<User> listByUsername(String username);
	
	/**
	 * 订单提交审核时候，查询可以审核的用户，模糊查询
	 * @param username
	 * @return
	 */
	public List<User> listOrderAuditor(String username);
	
	/**
	 * 返回业务员
	 * @param username
	 * @return
	 */
	public List<User> listSeller(String username);
	
	/**
	 * 返回跟单员
	 * @param username
	 * @return
	 */
	public List<User> listMerchandiser(String username);

	/**
	 * 通过一下参数
	 * @param user.setLoginname()
	 * @param user.setUsername()
	 * @param user.setUsercode()
	 * @param user.setState()
	 * @return
	 */
	public Pager<User> dataGridByUsers(User user);

	/**
	 * 修改密码
	 * @param userId 用户id
	 * @param pwd 新密码
	 * @return
	 */
	public void editUserPwdForAdmin(Integer userId, String password);

}
