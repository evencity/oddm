package com.apical.oddm.core.model.produce;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.apical.oddm.core.model.order.OrderInfo;

import java.util.Date;
import java.sql.Timestamp;
import java.util.Set;

/**
 * The persistent class for the order_manufacture database table.
 * 
 */
@SuppressWarnings("deprecation")
@Entity
@Table(name="order_manufacture")
@NamedQuery(name="Manufacture.findAll", query="SELECT o FROM Manufacture o")
public class Manufacture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Transient
	private Integer unread; //订单1未读，供传查询用，页面只显示1未读即可
	
	private String approver;

	private String auditor;

	@Temporal(TemporalType.DATE)
	@Column(name="date_issue")
	private Date dateIssue;

	@Temporal(TemporalType.DATE)
	@Column(name="date_shipment")
	private Date dateShipment;

	private String drafter;

	private String notice;

	private String remark;

	private Integer state;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	private String version;

	//bi-directional many-to-one association to OrderMftFitting
	@OneToMany(mappedBy="orderManufacture")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy()
	private Set<ManufactureFitting> orderMftFittings;

	//bi-directional many-to-one association to OrderMftO
	@OneToMany(mappedBy="orderManufacture")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy()
	private Set<ManufactureOs> orderMftOs;

	//bi-directional many-to-one association to OrderMftPackage
	@OneToMany(mappedBy="orderManufacture")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy()
	private Set<ManufacturePackage> orderMftPackages;

	//bi-directional many-to-one association to OrderMftShell
	@OneToMany(mappedBy="orderManufacture")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy()
	private Set<ManufactureShell> orderMftShells;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;
	
	/**
	 * 只供获取，不能set
	 */
	@Column(name="order_id", updatable=false, insertable=false)
	private Integer orderId;
	
	/***********订单相关***********/
	@Transient
	private String clientName;
	
	@Transient
	private String orderNo;
	
	public Manufacture() {
	}

	public Manufacture(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getClientName() {
		if (clientName == null && orderInfo != null) {
			return orderInfo.getClientName();
		}
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAuditor() {
		return this.auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Date getDateIssue() {
		return this.dateIssue;
	}

	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}

	public Date getDateShipment() {
		return this.dateShipment;
	}

	public void setDateShipment(Date dateShipment) {
		this.dateShipment = dateShipment;
	}

	public String getDrafter() {
		return this.drafter;
	}

	public void setDrafter(String drafter) {
		this.drafter = drafter;
	}

	public String getNotice() {
		return this.notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getOrderNo() {
		if (orderNo == null && orderInfo != null) {
			return orderInfo.getOrderNo();
		}
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Set<ManufactureFitting> getOrderMftFittings() {
		return this.orderMftFittings;
	}

	public void setOrderMftFittings(Set<ManufactureFitting> orderMftFittings) {
		this.orderMftFittings = orderMftFittings;
	}

	public ManufactureFitting addOrderMftFitting(ManufactureFitting orderMftFitting) {
		getOrderMftFittings().add(orderMftFitting);
		orderMftFitting.setOrderManufacture(this);

		return orderMftFitting;
	}

	public ManufactureFitting removeOrderMftFitting(ManufactureFitting orderMftFitting) {
		getOrderMftFittings().remove(orderMftFitting);
		orderMftFitting.setOrderManufacture(null);

		return orderMftFitting;
	}

	public Set<ManufactureOs> getOrderMftOs() {
		return this.orderMftOs;
	}

	public void setOrderMftOs(Set<ManufactureOs> orderMftOs) {
		this.orderMftOs = orderMftOs;
	}

	public ManufactureOs addOrderMftO(ManufactureOs orderMftO) {
		getOrderMftOs().add(orderMftO);
		orderMftO.setOrderManufacture(this);

		return orderMftO;
	}

	public ManufactureOs removeOrderMftO(ManufactureOs orderMftO) {
		getOrderMftOs().remove(orderMftO);
		orderMftO.setOrderManufacture(null);

		return orderMftO;
	}

	public Set<ManufacturePackage> getOrderMftPackages() {
		return this.orderMftPackages;
	}

	public void setOrderMftPackages(Set<ManufacturePackage> orderMftPackages) {
		this.orderMftPackages = orderMftPackages;
	}

	public ManufacturePackage addOrderMftPackage(ManufacturePackage orderMftPackage) {
		getOrderMftPackages().add(orderMftPackage);
		orderMftPackage.setOrderManufacture(this);

		return orderMftPackage;
	}

	public ManufacturePackage removeOrderMftPackage(ManufacturePackage orderMftPackage) {
		getOrderMftPackages().remove(orderMftPackage);
		orderMftPackage.setOrderManufacture(null);

		return orderMftPackage;
	}

	public Set<ManufactureShell> getOrderMftShells() {
		return this.orderMftShells;
	}

	public void setOrderMftShells(Set<ManufactureShell> orderMftShells) {
		this.orderMftShells = orderMftShells;
	}

	public ManufactureShell addOrderMftShell(ManufactureShell orderMftShell) {
		getOrderMftShells().add(orderMftShell);
		orderMftShell.setOrderManufacture(this);

		return orderMftShell;
	}

	public ManufactureShell removeOrderMftShell(ManufactureShell orderMftShell) {
		getOrderMftShells().remove(orderMftShell);
		orderMftShell.setOrderManufacture(null);

		return orderMftShell;
	}

	public Integer getUnread() {
		if (unread != null) return 1;
		return unread;
	}

	public void setUnread(Integer unread) {
		this.unread = unread;
	}
	/**
	 * 仅供hql语句拼接查询用
	 * @return
	 */
	public boolean isUnread() {
		if (unread != null && unread == 1) {
			return true;
		}
		return false;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}