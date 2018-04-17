package com.apical.oddm.core.model.sys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import com.apical.oddm.core.model.base.IdEntity;

/**
 * The persistent class for the sys_user database table.
 * 
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String loginname; // 登录名
	private String usercode; // 用户编码
	private String password; // 密码
	private String salt; // 密码
	private String username; // 姓名
	private Integer sex; // 性别
	
	private Date age; // 年龄
	private Date createdatetime; // 创建时间
	private Integer state; // 状态
	private String description;
	private Organization organization;
	private Set<Role> roles = new HashSet<Role>(0);

/*	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)  //报错了
	private Set<OrderInfo> orderInfos; 
	*/
	public User() {
		super();
	}

	public User(String loginname, String password, String username, Integer sex,
			Integer state, Organization organization) {
		super();
		this.loginname = loginname;
		this.password = password;
		this.setUsername(username);
		this.sex = sex;
		this.organization = organization;
		this.state = state;
	}

	public User(Integer id) {
		super(id);
	}
	
	public User(String username) {
		this.username = username;
	}
	/*public Set<OrderInfo> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(Set<OrderInfo> orderInfos) {
		this.orderInfos = orderInfos;
	}*/

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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getAge() {
		return age;
	}

	public void setAge(Date age) {
		this.age = age;
	}
//datetime和timestamp都用TemporalType.TIMESTAMP，字段类型为Date
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp", insertable = false, updatable = false)
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

	@ManyToMany(fetch = FetchType.LAZY)//立即加载，可以写有hql控制慢加载的 left join fetch
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	@OrderBy("seq asc")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@ManyToOne(fetch = FetchType.LAZY) //立即加载
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}