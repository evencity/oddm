package com.apical.oddm.core.model.encase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * The persistent class for the packing_info database table.
 * 
 */
@Entity
@Table(name="encase_info")
@NamedQuery(name="EncaseInfo.findAll", query="SELECT p FROM EncaseInfo p")
public class EncaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String address;

	private String company;

	private String description;

	private String homepage;

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="encase_date")
	private Date encaseDate;

	@Temporal(TemporalType.DATE)
	@Transient
	private Date encaseDateStart;//供传查询用
	
	@Temporal(TemporalType.DATE)
	@Transient
	private Date encaseDateEnd;//供传查询用
	
	private String telphone;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;
	
	@Column(name="unit_length")
	private String unitLength;

	@Column(name="unit_weight")
	private String unitWeight;

	private String zipcode;

	//bi-directional many-to-one association to PackingList
	@SuppressWarnings("deprecation")
	@OneToMany(mappedBy="encaseInfo")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy()
	private Set<EncaseList> encaseLists;

	@Transient
	private BigDecimal qtys;

/*	@Transient
	private Integer qtyCtns;*/
	
	public EncaseInfo() {
	}

	public EncaseInfo(Integer id) {
		super();
		this.id = id;
	}

	public BigDecimal getQtys() {
		return qtys;
	}

	public void setQtys(BigDecimal qtys) {
		this.qtys = qtys;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getUnitLength() {
		return this.unitLength;
	}

	public void setUnitLength(String unitLength) {
		this.unitLength = unitLength;
	}

	public String getUnitWeight() {
		return this.unitWeight;
	}

	public void setUnitWeight(String unitWeight) {
		this.unitWeight = unitWeight;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Date getEncaseDate() {
		return encaseDate;
	}

	public void setEncaseDate(Date encaseDate) {
		this.encaseDate = encaseDate;
	}

	public Set<EncaseList> getEncaseLists() {
		return encaseLists;
	}

	public void setEncaseLists(Set<EncaseList> encaseLists) {
		this.encaseLists = encaseLists;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Date getEncaseDateStart() {
		return encaseDateStart;
	}

	public void setEncaseDateStart(Date encaseDateStart) {
		this.encaseDateStart = encaseDateStart;
	}

	public Date getEncaseDateEnd() {
		return encaseDateEnd;
	}

	public void setEncaseDateEnd(Date encaseDateEnd) {
		this.encaseDateEnd = encaseDateEnd;
	}
	
}