package com.apical.oddm.facade.sys.command;

import java.util.Date;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月25日 上午11:15:52 
 * @version 1.0 
 */

public class UserCommand {

	private Integer id;
	private String loginname; // 登录名
	private String usercode; // 用户编码
	private String password; // 密码
	private String username; // 姓名
	
	private Integer sex; // 性别
	private String age; // 年龄
	private Date createdatetime; // 创建时间
	private Integer state; // 状态
	private String description;
	
	private Integer organizationId;
	private String organizationName;
	
	private String roleIds;
	private String roleNames;
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
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	@Override
	public String toString() {
		return "UserCommand [id=" + id + ", loginname=" + loginname + ", usercode=" + usercode + ", password=" + password + ", username=" + username + ", sex=" + sex + ", age="
				+ age + ", createdatetime=" + createdatetime + ", state=" + state + ", description=" + description + ", organizationId=" + organizationId + ", organizationName="
				+ organizationName + ", roleIds=" + roleIds + ", roleNames=" + roleNames + "]";
	}
	
}
