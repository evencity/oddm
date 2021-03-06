package com.apical.oddm.core.model.sys;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import com.apical.oddm.core.model.base.IdEntity;

/**
 * The persistent class for the sys_role database table.
 * 
 */
@Entity
@Table(name = "sys_role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Role extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name; // 角色名称
	private Integer seq; // 排序号
	private String description; // 备注
	private Set<Resource> resources = new HashSet<Resource>(0);
	private Set<User> users = new HashSet<User>(0);

	@NotBlank
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_resource", joinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "resource_id", nullable = false, updatable = false) })
	@OrderBy("seq ASC")
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	//@Cascade(value = { CascadeType.DELETE }) //会级联删除
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) })
	@OrderBy("id ASC")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
