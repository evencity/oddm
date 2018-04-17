package com.apical.oddm.core.model.sys;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import com.apical.oddm.core.model.base.IdEntity;

/**
 * The persistent class for the sys_organization database table.
 * 
 */
@Entity
@Table(name = "sys_organization", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Organization extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String icon;
	private Integer seq;
	private Organization organization;
	
	private Integer pid;
	
	private Set<Organization> organizations = new HashSet<Organization>(0);
	
	private Set<User> users = new HashSet<User>(0);

	public Organization() {
		super();
	}

	public Organization(Integer id) {
		super(id);
	}

	public Organization(String name, String icon, Integer seq,
			Organization organization, Set<Organization> organizations) {
		super();
		this.name = name;
		this.icon = icon;
		this.seq = seq;
		this.organization = organization;
		this.organizations = organizations;
	}

	@OneToMany(mappedBy="organization")   //关联另一个表的外键（由于本表没有关联字段）
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
	@OrderBy("seq asc")
	public Set<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}

	@Column(updatable=false, insertable=false)
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
}
