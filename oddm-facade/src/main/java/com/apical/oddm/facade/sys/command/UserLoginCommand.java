package com.apical.oddm.facade.sys.command;


/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月25日 上午10:54:08 
 * @version 1.0 
 */

public class UserLoginCommand {

	private Integer id;
	private String loginname; // 登录名
	private String password; // 密码

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
