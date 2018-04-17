package com.apical.oddm.facade.sys.dto;

import java.util.HashSet;
import java.util.Set;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月25日 上午11:10:08 
 * @version 1.0 
 */

public class RoleDTO {

private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name; // 角色名称
	private Integer seq; // 排序号
	private String description; // 备注
	private String resourceIds;
	private String resourceIdNames;
	
	public String getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	public String getResourceIdNames() {
		return resourceIdNames;
	}
	public void setResourceIdNames(String resourceIdNames) {
		this.resourceIdNames = resourceIdNames;
	}
	private Set<ResourceDTO> resources = new HashSet<ResourceDTO>(0);
	private Set<UserDTO> users = new HashSet<UserDTO>(0);
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<ResourceDTO> getResources() {
		return resources;
	}
	public void setResources(Set<ResourceDTO> resources) {
		this.resources = resources;
	}
	public Set<UserDTO> getUsers() {
		return users;
	}
	public void setUsers(Set<UserDTO> users) {
		this.users = users;
	}
	
	
}
