package com.apical.oddm.core.model.sys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import com.apical.oddm.core.model.base.IdEntity;

@Entity
@Table(name = "sys_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UserTest extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1867623281523381449L;

	private String loginname; // 登录名
	private String usercode; // 用户编码
	private String password; // 密码
	private String username; // 姓名
	
	private Gender sex; // 性别
	private Integer age; // 年龄
	private Date createdatetime; // 创建时间
	private Integer state; // 状态
	private String description;
	private Organization organization;
	private Set<Role> roles = new HashSet<Role>(0);

	public UserTest() {
		super();
	}

	public UserTest(String loginname, String password, String username, Gender sex, Integer age, Date createdatetime,
			Integer state, Organization organization) {
		super();
		this.loginname = loginname;
		this.password = password;
		this.setUsername(username);
		this.sex = sex;
		this.age = age;
		this.organization = organization;
		this.createdatetime = createdatetime;
		this.state = state;
	}

	@NotBlank
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.ORDINAL)
	public Gender getSex() {
		return sex;
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp", length = 19)
	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY) //延迟加载
	@JoinColumn(name = "organization_id")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@NotBlank
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}