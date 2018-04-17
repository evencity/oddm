package com.apical.oddm.facade.sys.dto;


/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月25日 下午12:16:53 
 * @version 1.0 
 */

public class OrganizationDTO {


	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String iconCls;
	private String icon;
	private Integer pid;
	private String pname;
	private Integer seq;
	
	private String text;
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
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
		return "OrganizationDTO [id=" + id + ", name=" + name + ", iconCls=" + iconCls + ", icon=" + icon + ", pid=" + pid + ", pname=" + pname + ", seq=" + seq + ", text=" + text
				+ "]";
	}
	
}
