package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the packing_info database table.
 * 
 */
@Entity
@Table(name="packing_info")
@NamedQuery(name="PackingInfo.findAll", query="SELECT p FROM PackingInfo p")
public class PackingInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String address;

	private String company;

	private String description;

	private String homepage;

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="packing_date")
	private Date packingDate;

	private String telphone;

	private Timestamp timestamp;

	@Column(name="unit_length")
	private String unitLength;

	@Column(name="unit_weight")
	private String unitWeight;

	private String zipcode;

	//bi-directional many-to-one association to PackingList
	@OneToMany(mappedBy="packingInfo")
	private Set<PackingList> packingLists;

	public PackingInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public Date getPackingDate() {
		return this.packingDate;
	}

	public void setPackingDate(Date packingDate) {
		this.packingDate = packingDate;
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

	public Set<PackingList> getPackingLists() {
		return this.packingLists;
	}

	public void setPackingLists(Set<PackingList> packingLists) {
		this.packingLists = packingLists;
	}

	public PackingList addPackingList(PackingList packingList) {
		getPackingLists().add(packingList);
		packingList.setPackingInfo(this);

		return packingList;
	}

	public PackingList removePackingList(PackingList packingList) {
		getPackingLists().remove(packingList);
		packingList.setPackingInfo(null);

		return packingList;
	}

}