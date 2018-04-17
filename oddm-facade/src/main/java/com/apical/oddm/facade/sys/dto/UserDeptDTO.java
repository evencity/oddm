package com.apical.oddm.facade.sys.dto;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月27日 下午2:13:57 
 * @version 1.0 
 */

public class UserDeptDTO {

	private String id;
	private String pid;
	private String text;
	private String iconCls;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	@Override
	public String toString() {
		return "UserDeptCommand [id=" + id + ", pid=" + pid + ", text=" + text + ", iconCls=" + iconCls + "]";
	}
}
