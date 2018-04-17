package com.apical.oddm.facade.sys.command;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月25日 下午12:51:22 
 * @version 1.0 
 */

public class RoleCommand {

	private Integer id;
	
	private String name; // 角色名称
	private Integer seq; // 排序号
	private String description; // 备注
	
	private String resourceIds;
	
	public String getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	@Override
	public String toString() {
		return "RoleCommand [id=" + id + ", name=" + name + ", seq=" + seq + ", description=" + description + "]";
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
	
}
