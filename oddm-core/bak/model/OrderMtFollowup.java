package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the order_mt_followup database table.
 * 
 */
@Entity
@Table(name="order_mt_followup")
@NamedQuery(name="OrderMtFollowup.findAll", query="SELECT o FROM OrderMtFollowup o")
public class OrderMtFollowup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String business;

	@Column(name="client_name")
	private String clientName;

	@Temporal(TemporalType.DATE)
	@Column(name="date_commit")
	private Date dateCommit;

	@Temporal(TemporalType.DATE)
	@Column(name="date_deliver")
	private Date dateDeliver;

	@Temporal(TemporalType.DATE)
	@Column(name="date_finish")
	private Date dateFinish;

	@Temporal(TemporalType.DATE)
	@Column(name="date_online")
	private Date dateOnline;

	@Temporal(TemporalType.DATE)
	@Column(name="date_submit")
	private Date dateSubmit;

	@Temporal(TemporalType.DATE)
	@Column(name="date_to")
	private Date dateTo;

	private String merchandiser;

	@Column(name="mt_code")
	private String mtCode;

	@Column(name="mt_condition")
	private String mtCondition;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="prod_line")
	private String prodLine;

	private int quality;

	private String section;

	private String specification;

	private byte state;

	//bi-directional many-to-one association to OrderMtFollowupAlter
	@OneToMany(mappedBy="orderMtFollowup")
	private Set<OrderMtFollowupAlter> orderMtFollowupAlters;

	public OrderMtFollowup() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBusiness() {
		return this.business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getDateCommit() {
		return this.dateCommit;
	}

	public void setDateCommit(Date dateCommit) {
		this.dateCommit = dateCommit;
	}

	public Date getDateDeliver() {
		return this.dateDeliver;
	}

	public void setDateDeliver(Date dateDeliver) {
		this.dateDeliver = dateDeliver;
	}

	public Date getDateFinish() {
		return this.dateFinish;
	}

	public void setDateFinish(Date dateFinish) {
		this.dateFinish = dateFinish;
	}

	public Date getDateOnline() {
		return this.dateOnline;
	}

	public void setDateOnline(Date dateOnline) {
		this.dateOnline = dateOnline;
	}

	public Date getDateSubmit() {
		return this.dateSubmit;
	}

	public void setDateSubmit(Date dateSubmit) {
		this.dateSubmit = dateSubmit;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getMerchandiser() {
		return this.merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	public String getMtCode() {
		return this.mtCode;
	}

	public void setMtCode(String mtCode) {
		this.mtCode = mtCode;
	}

	public String getMtCondition() {
		return this.mtCondition;
	}

	public void setMtCondition(String mtCondition) {
		this.mtCondition = mtCondition;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProdLine() {
		return this.prodLine;
	}

	public void setProdLine(String prodLine) {
		this.prodLine = prodLine;
	}

	public int getQuality() {
		return this.quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public Set<OrderMtFollowupAlter> getOrderMtFollowupAlters() {
		return this.orderMtFollowupAlters;
	}

	public void setOrderMtFollowupAlters(Set<OrderMtFollowupAlter> orderMtFollowupAlters) {
		this.orderMtFollowupAlters = orderMtFollowupAlters;
	}

	public OrderMtFollowupAlter addOrderMtFollowupAlter(OrderMtFollowupAlter orderMtFollowupAlter) {
		getOrderMtFollowupAlters().add(orderMtFollowupAlter);
		orderMtFollowupAlter.setOrderMtFollowup(this);

		return orderMtFollowupAlter;
	}

	public OrderMtFollowupAlter removeOrderMtFollowupAlter(OrderMtFollowupAlter orderMtFollowupAlter) {
		getOrderMtFollowupAlters().remove(orderMtFollowupAlter);
		orderMtFollowupAlter.setOrderMtFollowup(null);

		return orderMtFollowupAlter;
	}

}