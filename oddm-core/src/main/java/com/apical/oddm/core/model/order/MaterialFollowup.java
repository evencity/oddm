package com.apical.oddm.core.model.order;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.apical.oddm.infra.util.OddmDateUtil;


/**
 * The persistent class for the order_mt_followup database table.
 * 注意 订单号和物料号都可以重复
 */
@Entity
@Table(name="order_mt_followup")
@NamedQuery(name="MaterialFollowup.findAll", query="SELECT o FROM MaterialFollowup o")
public class MaterialFollowup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

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

	@Transient
	private Integer dateTo; // 到今天为止天数

	@Column(name="mt_code")
	private String mtCode;

	@Column(name="mt_condition")
	private String mtCondition;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY) //延迟加载
	@JoinColumn(name = "orderid")
	private OrderInfo orderInfo;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="orderid", updatable=false, insertable=false)
	private Integer orderId;
	
	@Column(name="prod_line")
	private String prodLine;

	private Integer quality;

	//private String section; //拆单

	private String specification;

	private Integer state;
	
	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	//bi-directional many-to-one association to OrderMtFollowupAlter
	@OneToMany(mappedBy="materialFollowup")
	@Cascade(value = { CascadeType.DELETE }) //会级联删除
	private Set<MaterialFollowupAlter> materialFollowupAlters;

	public MaterialFollowup() {
	}

	public MaterialFollowup(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
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

	public Integer getDateTo() {
		return this.dateTo = OddmDateUtil.getDayDiffer(dateCommit);
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

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getProdLine() {
		return this.prodLine;
	}

	public void setProdLine(String prodLine) {
		this.prodLine = prodLine;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Set<MaterialFollowupAlter> getMaterialFollowupAlters() {
		return materialFollowupAlters;
	}

	public void setMaterialFollowupAlters(
			Set<MaterialFollowupAlter> materialFollowupAlters) {
		this.materialFollowupAlters = materialFollowupAlters;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}