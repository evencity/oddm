package com.apical.oddm.web.controller.sysmaint;

import java.util.Map;

/**
 * 统计一些参数
 * @author lgx
 * 2016-12-23
 */
public class Monitor {
	/**
	 * 登录的用户，多个相同用户登录算一个
	 */
	private Integer loginUser;
	
	/**
	 * 登录的用户，多个相同用户登录算多个
	 */
	private Integer loginUsers;

	/**
	 * 所有在线的用户
	 */
	private Map<String, Integer> userNameMap;
	
	public Integer getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Integer loginUser) {
		this.loginUser = loginUser;
	}

	public Integer getLoginUsers() {
		return loginUsers;
	}

	public void setLoginUsers(Integer loginUsers) {
		this.loginUsers = loginUsers;
	}

	public Map<String, Integer> getUserNameMap() {
		return userNameMap;
	}

	public void setUserNameMap(Map<String, Integer> userNameMap) {
		this.userNameMap = userNameMap;
	}

}
