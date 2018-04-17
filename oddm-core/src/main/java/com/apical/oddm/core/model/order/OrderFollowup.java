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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * The persistent class for the order_followup database table.
 * 
 */
@Entity
@Table(name="order_followup")
@NamedQuery(name="OrderFollowup.findAll", query="SELECT o FROM OrderFollowup o")
public class OrderFollowup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer agency;

	@Column(name="boot_logo")
	private Integer bootLogo;

	private Integer carton;

	private Integer colorbox;

	private Integer fitting;

	private Integer hardware;

	private Integer inspection;

	private Integer map;

	private Integer membrane;

	private Integer packing;

	private Integer payment;

	private String plan;

	@Column(name="pre_file")
	private Integer preFile;

	private Integer shell;

	private Integer sorfware;

	private Integer specification;

	private Integer state;

	private Integer tags;

	private Integer uuid;

	private String description;
	
	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	@Temporal(TemporalType.DATE)
	@Column(name="date_client")
	private Date dateClient;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_factory")
	private Date dateFactory; //计划回复交期

	private String status;
	
	@Column(name="status_abnormal")
	private String statusAbnormal;

	private String level;

	@Column(name="duty_officer")
	private String dutyOfficer;
	
	private String solution;
	
	private String  shipment;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="order_id", updatable=false, insertable=false)
	private Integer orderId;
	
	@OneToMany(mappedBy="orderFollowup")
	@Cascade(value = { CascadeType.DELETE }) //会级联删除
	private Set<OrderFollowupAlter> orderFollowupAlters;

	/***********订单相关***********/
	@Transient
	private Integer merchandiserId;
	@Transient
	private Integer sellerId;
	@Transient
	private String merchandiser;//所属跟单
	@Transient
	private String seller; //所属业务
	@Transient
	private String clientName; //客户名称
	@Transient
	private String clientNameCode; //客户名称
	@Transient
	private String productFactory;//工厂机型
	@Transient
	private String productClient;//工厂机型
	@Transient
	private Date dateDelivery;//交货日期
	@Transient
	private Date dateOrder; //下单时间
	@Transient
	private String orderNo;
	@Transient
	private Integer quantity;
	@Transient
	private String district;
	
	public OrderFollowup() {
	}

	public OrderFollowup(Integer id) {
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
	
	public Date getDateFactory() {
		return dateFactory;
	}

	public void setDateFactory(Date dateFactory) {
		this.dateFactory = dateFactory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAgency() {
		return this.agency;
	}

	public void setAgency(Integer agency) {
		this.agency = agency;
	}

	public Integer getBootLogo() {
		return this.bootLogo;
	}

	public void setBootLogo(Integer bootLogo) {
		this.bootLogo = bootLogo;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getCarton() {
		return this.carton;
	}

	public void setCarton(Integer carton) {
		this.carton = carton;
	}

	public Integer getColorbox() {
		return this.colorbox;
	}

	public void setColorbox(Integer colorbox) {
		this.colorbox = colorbox;
	}

	public Date getDateClient() {
		return this.dateClient;
	}

	public void setDateClient(Date dateClient) {
		this.dateClient = dateClient;
	}

	public Integer getFitting() {
		return this.fitting;
	}

	public void setFitting(Integer fitting) {
		this.fitting = fitting;
	}

	public Integer getHardware() {
		return this.hardware;
	}

	public void setHardware(Integer hardware) {
		this.hardware = hardware;
	}

	public Integer getInspection() {
		return this.inspection;
	}

	public void setInspection(Integer inspection) {
		this.inspection = inspection;
	}

	public Integer getMap() {
		return this.map;
	}

	public void setMap(Integer map) {
		this.map = map;
	}

	public Integer getMembrane() {
		return this.membrane;
	}

	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}

	public Integer getPacking() {
		return this.packing;
	}

	public void setPacking(Integer packing) {
		this.packing = packing;
	}

	public Integer getPayment() {
		return this.payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Integer getPreFile() {
		return this.preFile;
	}

	public void setPreFile(Integer preFile) {
		this.preFile = preFile;
	}

	public Integer getShell() {
		return this.shell;
	}

	public void setShell(Integer shell) {
		this.shell = shell;
	}

	public Integer getSorfware() {
		return this.sorfware;
	}

	public void setSorfware(Integer sorfware) {
		this.sorfware = sorfware;
	}

	public Integer getSpecification() {
		return this.specification;
	}

	public void setSpecification(Integer specification) {
		this.specification = specification;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getTags() {
		return this.tags;
	}

	public void setTags(Integer tags) {
		this.tags = tags;
	}

	public Integer getUuid() {
		return this.uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Set<OrderFollowupAlter> getOrderFollowupAlters() {
		return this.orderFollowupAlters;
	}

	public void setOrderFollowupAlters(Set<OrderFollowupAlter> orderFollowupAlters) {
		this.orderFollowupAlters = orderFollowupAlters;
	}

	public OrderFollowupAlter addOrderFollowupAlter(OrderFollowupAlter orderFollowupAlter) {
		getOrderFollowupAlters().add(orderFollowupAlter);
		orderFollowupAlter.setOrderFollowup(this);

		return orderFollowupAlter;
	}

	public OrderFollowupAlter removeOrderFollowupAlter(OrderFollowupAlter orderFollowupAlter) {
		getOrderFollowupAlters().remove(orderFollowupAlter);
		orderFollowupAlter.setOrderFollowup(null);

		return orderFollowupAlter;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMerchandiserId() {
		return merchandiserId;
	}

	public void setMerchandiserId(Integer merchandiserId) {
		this.merchandiserId = merchandiserId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getMerchandiser() {
		return merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientNameCode() {
		return clientNameCode;
	}

	public void setClientNameCode(String clientNameCode) {
		this.clientNameCode = clientNameCode;
	}

	public String getProductFactory() {
		return productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public String getProductClient() {
		return productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

	public Date getDateDelivery() {
		return dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStatusAbnormal() {
		return statusAbnormal;
	}

	public void setStatusAbnormal(String statusAbnormal) {
		this.statusAbnormal = statusAbnormal;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDutyOfficer() {
		return dutyOfficer;
	}

	public void setDutyOfficer(String dutyOfficer) {
		this.dutyOfficer = dutyOfficer;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getShipment() {
		return shipment;
	}

	public void setShipment(String shipment) {
		this.shipment = shipment;
	}
	
}