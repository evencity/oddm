package com.apical.oddm.facade.base.dto;

import java.util.List;

/**
 * 树形模板
 * @author apical
 *
 */
public class Tree implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String text;//显示节点文本。
	private String state = "open";//'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
	private boolean checked = false;//表示该节点是否被选中。
	private Object attributes; //被添加到节点的自定义属性。
	private List<Tree> children; //一个节点数组声明了若干节点。
	private String iconCls; //节点图标
	private String pid; // 节点的父级id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
