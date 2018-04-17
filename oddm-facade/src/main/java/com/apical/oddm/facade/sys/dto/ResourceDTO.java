package com.apical.oddm.facade.sys.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月25日 上午11:09:11 
 * @version 1.0 
 */

public class ResourceDTO {

	private Integer id; // id
	private String createdatetime; // 创建时间
	private String name; // 名称
	private String url; // 菜单路径
	private String description; // 描述
	private String icon; // 图标
	private Integer resourcetype; // 资源类型, 0菜单 1功能
	private ResourceDTO resourceDTO; // 父级pid
	private Integer state; // 状态 0启用 1停用
	private Integer pid;
	private String pname;
	private Integer seq; // 排序号

	private String text;
	private boolean checked = false;
	private Object attributes;
	private List<ResourceDTO> children;
	private String iconCls;
	
	private Set<RoleDTO> roles = new HashSet<RoleDTO>(0);
	private Set<ResourceDTO> resources = new HashSet<ResourceDTO>(0);
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreatedatetime() {
		return createdatetime;
	}
	public void setCreatedatetime(String createdatetime) {
		this.createdatetime = createdatetime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getResourcetype() {
		return resourcetype;
	}
	public void setResourcetype(Integer resourcetype) {
		this.resourcetype = resourcetype;
	}
	public ResourceDTO getResourceDTO() {
		return resourceDTO;
	}
	public void setResourceDTO(ResourceDTO resourceDTO) {
		this.resourceDTO = resourceDTO;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Object getAttributes() {
		return attributes;
	}
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	public List<ResourceDTO> getChildren() {
		return children;
	}
	public void setChildren(List<ResourceDTO> children) {
		this.children = children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Set<RoleDTO> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}
	public Set<ResourceDTO> getResources() {
		return resources;
	}
	public void setResources(Set<ResourceDTO> resources) {
		this.resources = resources;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
