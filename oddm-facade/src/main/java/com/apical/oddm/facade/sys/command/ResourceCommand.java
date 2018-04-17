package com.apical.oddm.facade.sys.command;

import java.util.Date;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月25日 下午12:38:41 
 * @version 1.0 
 */

public class ResourceCommand {

	private Integer pid;
	private String pname;

	private Integer id;
	private Date createdatetime; // 创建时间
	private String name; // 名称
	private String url; // 菜单路径
	private String description; // 描述
	private String iconCls; // 图标
	private Integer seq; // 排序号
	private Integer resourcetype; // 资源类型, 0菜单 1功能
	private Integer Cstate; // 状态 0启用 1停用
	private String icon;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public void setCreatedatetime(Date createdatetime) {
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
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getResourcetype() {
		return resourcetype;
	}
	public void setResourcetype(Integer resourcetype) {
		this.resourcetype = resourcetype;
	}
	public Integer getCstate() {
		return Cstate;
	}
	public void setCstate(Integer cstate) {
		Cstate = cstate;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public String toString() {
		return "ResourceCommand [pid=" + pid + ", pname=" + pname + ", id=" + id + ", createdatetime=" + createdatetime + ", name=" + name + ", url=" + url + ", description="
				+ description + ", iconCls=" + iconCls + ", seq=" + seq + ", resourcetype=" + resourcetype + ", Cstate=" + Cstate + ", icon=" + icon + "]";
	}
	
	
}
