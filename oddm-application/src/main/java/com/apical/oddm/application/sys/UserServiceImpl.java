package com.apical.oddm.application.sys;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.document.DocumentUnreadDaoI;
import com.apical.oddm.core.dao.order.OrderInfoDaoI;
import com.apical.oddm.core.dao.order.OrderUnreadDaoI;
import com.apical.oddm.core.dao.sys.OrganizationDaoI;
import com.apical.oddm.core.dao.sys.ResourceDaoI;
import com.apical.oddm.core.dao.sys.SysConfigDaoI;
import com.apical.oddm.core.dao.sys.UserDaoI;
import com.apical.oddm.core.exception.OddmRuntimeException;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.core.model.sys.SysConfig;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.infra.encrypt.Encrypt;
import com.apical.oddm.infra.encrypt.RSAUtil;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserServiceI {
    
	@Autowired
	private UserDaoI userDao;
	
	@Autowired
	private OrderUnreadDaoI orderUnreadDao;
	
	@Autowired
	private DocumentUnreadDaoI documentUnreadDao;
/*	
	@Autowired
	private OrderInfoDaoI orderInfoDao;*/
	
	@Autowired
	private SysConfigDaoI sysConfigDao;
	
	@Autowired
	private ResourceDaoI resourceDao;
	
	@Autowired
	private OrganizationDaoI organizationDao;
	
	@Autowired
	private OrderInfoDaoI orderInfoDao;
	
	@Override
	public Pager<User> dataGrid() {
		return userDao.dataGrid();
	}

	@Override
	public Serializable add(User user) {
		String uuid = UUID.randomUUID()+"";
		user.setSalt(uuid);
		if (StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(Encrypt.sha256Hex(user.getPassword()+uuid));
		}
		return userDao.add(user);
	}

	@Override
	public void delete(int userId) {
		documentUnreadDao.deleteAllUserId(userId);
		orderUnreadDao.deleteAllUserId(userId);
		orderInfoDao.updateUserIdNull(userId);
		userDao.delete(userId);
	}

	@Override
	public void edit(User user) {
		userDao.update(user); //不能同时再查一次， 否则一个session同一个user org.hibernate.NonUniqueObjectException: A different object with the same identifier value was already associated with the session : 
	}

	@Override
	public User get(int id, boolean lazy) {
		return userDao.get(id, lazy);
	}

	@Override
	public User login(User user) {
		String loginName = user.getLoginname();
		String password = user.getPassword();
		if (!StringUtils.isNotBlank(loginName) || !StringUtils.isNotBlank(password)) {
			//throw new OddmRuntimeException("用户名或密码不能为空！");
			return null;
		}
		password = RSAUtil.decrypt(password);
		if (password == null) return null;
		User u = userDao.getByLoginName(loginName);
		if (u == null) return null;
		user.setPassword(Encrypt.sha256Hex(password+u.getSalt()));
		return userDao.login(user);
	}

	@Override
	public List<Resource> getUserResource(int userId) {
		return resourceDao.getUserResource(userId);	
	}
	
	@Override
	public User getByLoginName(String loginname) {
		if (!StringUtils.isNotBlank(loginname)) {
			throw new OddmRuntimeException("用户名不能为空！");
		}
		return userDao.getByLoginName(loginname);
	}

	@Override
	public Pager<User> getUserListByUserState(int state) {
		User u = new User();
		u.setState(state);
		return userDao.dataGridByUsers(u);
	}

	@Override
	public boolean editUserPwd(int userId, String oldPwd, String pwd) {
		if (!StringUtils.isNotBlank(oldPwd)) {
			throw new OddmRuntimeException("原密码不能为空！");
		}
		if (!StringUtils.isNotBlank(pwd)) {
			throw new OddmRuntimeException("新密码不能为空！");
		}
		User user = userDao.get(userId);
		oldPwd = Encrypt.sha256Hex(oldPwd+user.getSalt());
		if (oldPwd.equals(user.getPassword())) {
			user.setPassword(Encrypt.sha256Hex(pwd+user.getSalt()));
			userDao.update(user);
			return true;
		} else {
			throw new OddmRuntimeException("原密码错误！");
		}
	}

	@Override
	public boolean updatePassword(int userId) {
		User user = userDao.get(userId);
		user.setSalt(UUID.randomUUID()+"");
		SysConfig sysConfig = sysConfigDao.get("default_p");
		String defau = "asdf111!@#";
		if (sysConfig != null && StringUtils.isNotBlank(sysConfig.getValue())) {
			defau = sysConfig.getValue();
		}
		user.setPassword(Encrypt.sha256Hex(defau+user.getSalt()));
		userDao.update(user);
		return true;
	}

	@Override
	public boolean updateDefaultPassword(int userId) {
		User user = userDao.get(userId);
		SysConfig sysConfig2 = sysConfigDao.get("default_b");
		String defauBefore = "apk123!@#";
		if (sysConfig2 != null && StringUtils.isNotBlank(sysConfig2.getValue())) {
			defauBefore = sysConfig2.getValue();
		}
		SysConfig sysConfig = sysConfigDao.get("default_p");
		String defau = "asdf111!@#";
		if (sysConfig != null && StringUtils.isNotBlank(sysConfig.getValue())) {
			defau = sysConfig.getValue();
		}
		if (user != null && StringUtils.isNotBlank(user.getPassword()))  {
			if (user.getPassword().equals(Encrypt.sha256Hex(defauBefore+user.getSalt()))) {//如果是默认密码则改为其他的
				user.setSalt(UUID.randomUUID()+"");
				user.setPassword(Encrypt.sha256Hex(defau+user.getSalt()));
				userDao.update(user);
			}
		}
		return true;
	}
	
	@Override
	public Pager<User> getByUsername(String username) {
		User u = new User();
		u.setUsername(username);
		return userDao.dataGridByUsers(u);
	}

	@Override
	public List<User> getUserListByOrganization(int orgId) {
		return userDao.getUserListByOrganization(orgId);
	}
/*	
	//得把password改为char64
	public static void main(String[] args) {
		String s = Encrypt.sha256Hex("123456"+"33e8c5d7-da24-4024-833f-77e5d8f5494b");
		System.out.println(s.length());
		System.out.println(s);
	}*/

	@Override
	public User getByUsName(String username, boolean lazy) {
		return userDao.getByUsName(username, lazy);
	}

	@Override
	public List<User> listByUsername(String username) {
		return userDao.listByUsername(null, username);
	}

	@Override
	public Pager<User> dataGridByUsers(User user) {
		return userDao.dataGridByUsers(user);
	}

	@Override
	public List<User> listOrderAuditor(String username) {
		SysConfig sysConfig = sysConfigDao.get("order_auditor_role");
		//System.out.println("sysConfigsysConfigsysConfig "+sysConfig.getValue());
		if(sysConfig == null || StringUtils.isBlank(sysConfig.getValue())) {
			sysConfig = new SysConfig();
			sysConfig.setValue("销售|admin");
		} 
		String[] split = sysConfig.getValue().split("\\|");
		HashSet<String> roleNames = new HashSet<String>();
		for (String role : split) {
			//System.out.println("rolerolerolerole  "+role);
			roleNames.add(role);
		}
		return userDao.listByUsername(roleNames, username);
	}

	@Override
	public List<User> listSeller(String username) {
		SysConfig sysConfig = sysConfigDao.get("order_seller");
		if(sysConfig == null || StringUtils.isBlank(sysConfig.getValue())) {
			sysConfig = new SysConfig();
			sysConfig.setValue("销售");
		}
		String[] split = sysConfig.getValue().split("\\|");
		HashSet<String> roleNames = new HashSet<String>();
		for (String role : split) {
			//System.out.println("rolerolerolerole  "+role);
			roleNames.add(role);
		}
		return userDao.listByUsername(roleNames, username);
	}

	@Override
	public List<User> listMerchandiser(String username) {
		SysConfig sysConfig = sysConfigDao.get("order_merchandiser");
		if(sysConfig == null || StringUtils.isBlank(sysConfig.getValue())) {
			sysConfig = new SysConfig();
			sysConfig.setValue("跟单");
		}
		String[] split = sysConfig.getValue().split("\\|");
		HashSet<String> roleNames = new HashSet<String>();
		for (String role : split) {
			//System.out.println("rolerolerolerole  "+role);
			roleNames.add(role);
		}
		return userDao.listByUsername(roleNames, username);
	}

	@Override
	public List<User> listUsersWithOrderResource() {
		SysConfig sysConfig = sysConfigDao.get("order_unread_flag");
		return listUsersWithSysConfig(sysConfig);
	}
	
	@Override
	public List<User> listUsersWithDocDownResource() {
		SysConfig sysConfig = sysConfigDao.get("doc_unread_flag");
		return listUsersWithSysConfig(sysConfig);
	}

	@Override
	public List<User> listUsersWithMftResource() {
		SysConfig sysConfig = sysConfigDao.get("mft_unread_flag");
		return listUsersWithSysConfig(sysConfig);
	}

	@Override
	public List<User> listUsersWithBomResource() {
		SysConfig sysConfig = sysConfigDao.get("bom_unread_flag");
		return listUsersWithSysConfig(sysConfig);
	}
	
	//公共调用，查出那些用户具有 配置项里面的菜单名
	private List<User> listUsersWithSysConfig(SysConfig sysConfig) {
		//System.out.println("sysConfigsysConfigsysConfig "+sysConfig.getValue());
		if(sysConfig == null || StringUtils.isBlank(sysConfig.getValue())) {
			return userDao.listUsersWithOrderResource(null);
		} else {
			String[] split = sysConfig.getValue().split("\\|");
			HashSet<String> resouces = new HashSet<String>();
			for (String resouce : split) {
				resouces.add(resouce);
			}
			return userDao.listUsersWithOrderResource(resouces);
		}
	}

	@Override
	public List<User> listUsersWithSalePoResource() {
		SysConfig sysConfig = sysConfigDao.get("sale_po_unread_flag");
		return listUsersWithSysConfig(sysConfig);
	}

	@Override
	public void editUserPwdForAdmin(Integer userId, String password) {
		if (!StringUtils.isNotBlank(password)) {
			throw new OddmRuntimeException("新密码不能为空！");
		}
		User user = userDao.get(userId);
		user.setPassword(Encrypt.sha256Hex(password+user.getSalt()));
		userDao.update(user);
			
	}
}
