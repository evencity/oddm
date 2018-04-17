package com.apical.oddm.core.model.sys;

import java.util.Date;

/**
 * 用来测试：
 	public List<OrganizationTest> findBySql(String sql) {
		SQLQuery q = this.getSession().createSQLQuery(sql);
		return q.list();
	}
	结论：
 * @author lgx
 * 2016-10-13
 */
public class OrganizationTest implements java.io.Serializable {

	private static final long serialVersionUID = -5980015965153438022L;
	
	private Integer id;
	private String name;
	private String icon;
	private Integer seq;
	private Integer pid;
	private Date timestamp;
	
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
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
