package com.apical.oddm.web.pageModel.base;

import java.util.List;

public class SessionInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 用户ID
	private String loginname;// 登录名
	private String name;// 姓名
	private Integer sex;// 性别
	private String ip;// 用户IP
	private Boolean hasRoles;//用户查看用户编码的权限
	
	private List<String> resourceList;// 用户可以访问的资源地址列表
	
	private List<String> resourceAllList;

	public List<String> getResourceList() {
		return resourceList;
	}
	
	public Boolean getHasRoles() {
		return hasRoles;
	}
	
	public void setHasRoles(Boolean hasRoles) {
		this.hasRoles = hasRoles;
	}
	
	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

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
	
	public List<String> getResourceAllList() {
		return resourceAllList;
	}

	public void setResourceAllList(List<String> resourceAllList) {
		this.resourceAllList = resourceAllList;
	}

	@Override
	public String toString() {
		final int maxLen = 100;
		return "SessionInfo [id=" + id + ", loginname=" + loginname + ", name=" + name + ", ip=" + ip + ", hasRoles=" + hasRoles + ", resourceList="
				+ (resourceList != null ? resourceList.subList(0, Math.min(resourceList.size(), maxLen)) : null) + ", resourceAllList="
				+ (resourceAllList != null ? resourceAllList.subList(0, Math.min(resourceAllList.size(), maxLen)) : null) + "]";
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

}
