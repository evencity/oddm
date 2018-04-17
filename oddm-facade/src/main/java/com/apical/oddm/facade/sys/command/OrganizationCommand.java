package com.apical.oddm.facade.sys.command;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月25日 下午12:52:45 
 * @version 1.0 
 */

public class OrganizationCommand {

	private Integer id;
	private String name;
	private String iconCls;
	private String icon;
	private Integer seq;
	private Integer pid;
	private String pname;
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
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
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
	@Override
	public String toString() {
		return "OrganizationCommand [id=" + id + ", name=" + name + ", iconCls=" + iconCls + ", icon=" + icon + ", seq=" + seq + ", pid=" + pid + ", pname=" + pname + "]";
	}
	
}
