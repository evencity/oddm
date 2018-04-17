package com.apical.oddm.facade.sys;

import java.util.List;

import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.sys.command.UserCommand;
import com.apical.oddm.facade.sys.command.UserLoginCommand;
import com.apical.oddm.facade.sys.dto.ResourceDTO;
import com.apical.oddm.facade.sys.dto.UserDTO;


public interface UserFacade {
	
	
	/**
	 * 登录验证
	 * @param user 传入登录名和密码
	 * @return 验证不通过则返回null
	 */
	public UserDTO login(UserLoginCommand userLoginCommand);
	
	/**
	 * 通过用户表id获取用户拥有的资源id
	 * @param userId 用户表id
	 * @return
	 */
	public List<ResourceDTO> getUserResource(int userId);
	

	/**
	 * 分页获取用户信息
	 * @return 立即加载对象
	 */
	public BasePage<UserDTO> dataGrid(UserCommand userCommand,PageCommand pageCommand);


	
	/**
	 * 增加用户
	 * @param user 用户对象
	 * @return
	 */
	public Boolean add(UserCommand user);

	/**
	 * 删除用户
	 * @param id 用户表id
	 */
	public void delete(Integer id);

	/**
	 * @param user
	 */
	public void edit(UserCommand user);

	/**
	 * 查单个用户信息
	 * @param id
	 * @return 快加载
	 */
	public UserDTO get(Integer id);

	/**
	 * 修改密码
	 * @param userId 用户id
	 * @param oldPwd 老密码
	 * @param pwd 新密码
	 * @return
	 */
	public boolean editUserPwd(Integer userId, String oldPwd, String pwd);
	
	/**
	 * 重置用户密码为默认密码
	 * @param userId
	 * @return
	 */
	public boolean updatePassword(Integer userId);

	/**
	 * 查询单个用户
	 * @param loginname 传入登录名
	 * @return 快加载
	 */
	public UserDTO getByLoginName(String loginname);
	

	/**
	 * 查询单个用户
	 * @param username
	 * @param lazy true为慢加载， false为快加载
	 * @return 
	 */
	public Boolean getByUsName(String username);
	
	/**
	 * 通过机构id获取所用用户
	 * @param orgId
	 * @return
	 */
	public List<UserDTO> getUserListByOrganization(Integer orgId);
	
	/**
	 * 通过用户名查询用户信息，半模糊查询 username%
	 * @param username
	 * @return
	 */
	public List<UserDTO> listByUsername(String username);
	/**
	 * 订单提交审核时候，查询可以审核的用户，半模糊查询 username%
	 * @param username
	 * @return
	 */
	public List<UserDTO> listOrderAuditor(String username);
	
	/**
	 * 返回业务员
	 * @param username
	 * @return
	 */
	public List<UserDTO> listSeller(String username);
	
	/**
	 * 返回跟单员
	 * @param username
	 * @return
	 */
	public List<UserDTO> listMerchandiser(String username);
	/**
	 * 修改密码管理员
	 * @param userId 用户id
	 * @param pwd 新密码
	 * @return
	 */
	public boolean editUserPwdForAdmin(Integer userId, String password);
}
