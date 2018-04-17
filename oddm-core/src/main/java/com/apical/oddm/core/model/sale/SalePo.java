package com.apical.oddm.core.model.sale;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.apical.oddm.core.model.order.OrderInfo;


/**
 * The persistent class for the sale_po database table.
 * 
 */
@SuppressWarnings("deprecation")
@Entity
@Table(name="sale_po")
@NamedQuery(name="SalePo.findAll", query="SELECT s FROM SalePo s")
public class SalePo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Transient
	private Integer unread; //1未读，供传查询用，页面只显示1未读即可
	
	private String company;
	
	private String address;
	
	private String tel;
	
	private String fax;

	private String homepage;
	
	@Column(name="pi_no")
	private String piNo;//备注

	@Temporal(TemporalType.DATE)
	@Column(name="date_examine")
	private Date dateExaminePre;

	private String description;

	private String currency;

	private String maker;

	@Column(name="approver_id")
	private Integer approverId;

	private String approver;//批准

	private String pm;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	private Integer state;
	
	//订单相关的属性
	@Transient
	private String orderNo; //所属业务
	@Transient
	private String merchandiser;//所属跟单
	@Transient
	private String seller; //所属业务
	@Transient
	private String clientName; //客户名称
	@Transient
	private String clientNameCode; //客户名称
	@Transient
	private String productClient; //客户机型
	@Transient
	private String productFactory;//工厂机型
	@Transient
	private Date dateDelivery;//交货日期
	@Transient
	private Date dateOrder; //下单时间
	
	//bi-directional many-to-one association to SalePoList
	@OneToMany(mappedBy="salePo")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy()
	private Set<SalePoList> salePoLists;

	@OneToMany(mappedBy="salePo", fetch=FetchType.LAZY)
	@Cascade(value = {CascadeType.DELETE}) //会级联增删改
	private Set<SalePoAlt> salePoAlts; //变更记录
	
	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="order_id", updatable=false, insertable=false)
	private Integer orderId;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public SalePo() {
	}

	public SalePo(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getDateExaminePre() {
		return dateExaminePre;
	}

	public void setDateExaminePre(Date dateExaminePre) {
		this.dateExaminePre = dateExaminePre;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaker() {
		return this.maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getPm() {
		return this.pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
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

	public Set<SalePoList> getSalePoLists() {
		return this.salePoLists;
	}

	public void setSalePoLists(Set<SalePoList> salePoLists) {
		this.salePoLists = salePoLists;
	}

	public SalePoList addSalePoList(SalePoList salePoList) {
		getSalePoLists().add(salePoList);
		salePoList.setSalePo(this);

		return salePoList;
	}

	public SalePoList removeSalePoList(SalePoList salePoList) {
		getSalePoLists().remove(salePoList);
		salePoList.setSalePo(null);

		return salePoList;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

	public String getPiNo() {
		return piNo;
	}

	public void setPiNo(String PiNo) {
		this.piNo = PiNo;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductClient() {
		return productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

	public Set<SalePoAlt> getSalePoAlts() {
		return salePoAlts;
	}

	public void setSalePoAlts(Set<SalePoAlt> salePoAlts) {
		this.salePoAlts = salePoAlts;
	}

	/**
	 * 获取本是否已经被读情况
	 * @return 1 未读 
	 */
	public Integer getUnread() {
		if (unread != null) return 1;
		return unread;
	}
	
	/**
	 * @param read 1 未读 
	 */
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
}