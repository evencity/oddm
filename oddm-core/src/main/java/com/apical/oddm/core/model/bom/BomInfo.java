package com.apical.oddm.core.model.bom;

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
 * The persistent class for the bom_info database table.
 * 
 */
@Entity
@Table(name="bom_info")
public class BomInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Transient
	private Integer unread; //订单1未读，供传查询用，页面只显示1未读即可
	
	private String brand;//品牌

	private String description;

	@Column(name="material_code")
	private String materialCode;

	@Column(name="product_name")
	private String productName; //品名

	private String specification;

	private Integer state;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	private String maker;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_")
	private Date date; //日期
	
	private String version;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	//bi-directional many-to-one association to BomMaterialRef
	@SuppressWarnings("deprecation")
	@OneToMany(mappedBy="bomInfo")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy()
	private Set<BomMaterialRef> bomMaterialRefs;

	@OneToMany(mappedBy="bomInfo")
	@Cascade(value = { CascadeType.DELETE}) //会级联删
	@OrderBy()
	private Set<BomInfoAlt> bomInfoAlt;
	
	/**
	 * 只供获取，不能set
	 */
	@Column(name="order_id", updatable=false, insertable=false)
	private Integer orderId;
	
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
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public BomInfo() {
	}

	public BomInfo(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<BomInfoAlt> getBomInfoAlt() {
		return bomInfoAlt;
	}

	public void setBomInfoAlt(Set<BomInfoAlt> bomInfoAlt) {
		this.bomInfoAlt = bomInfoAlt;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Set<BomMaterialRef> getBomMaterialRefs() {
		return this.bomMaterialRefs;
	}

	public void setBomMaterialRefs(Set<BomMaterialRef> bomMaterialRefs) {
		this.bomMaterialRefs = bomMaterialRefs;
	}

	public BomMaterialRef addBomMaterialRef(BomMaterialRef bomMaterialRef) {
		getBomMaterialRefs().add(bomMaterialRef);
		bomMaterialRef.setBomInfo(this);

		return bomMaterialRef;
	}

	public BomMaterialRef removeBomMaterialRef(BomMaterialRef bomMaterialRef) {
		getBomMaterialRefs().remove(bomMaterialRef);
		bomMaterialRef.setBomInfo(null);

		return bomMaterialRef;
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
	
}