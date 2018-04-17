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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.produce.Manufacture;
import com.apical.oddm.core.model.produce.OrderPrototype;
import com.apical.oddm.core.model.produce.OrderRecord;
import com.apical.oddm.core.model.sale.SaleInfo;
import com.apical.oddm.core.model.sale.SalePo;


/**
 * The persistent class for the order_info database table.
 * 
 */
@SuppressWarnings("deprecation")
@Entity
@Table(name="order_info")
@NamedQuery(name="OrderInfo.findAll", query="SELECT o FROM OrderInfo o")
public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private String dateOrderMonth; //月份
	@Transient
	private String productType;

	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateOrderStart;//下单时间，供传查询用
	
	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateOrderEnd;//下单时间，供传查询用
	
	@Transient
	private Integer unread; //订单 1未读，供传查询用，页面只显示1未读即可
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="merchandiser_id")
	private Integer merchandiserId;

	@Column(name="seller_id")
	private Integer sellerId;

	private String merchandiser;//所属跟单

	//@Column(name="merchandiser_code")
	@Transient
	@Deprecated
	private String merchandiserCode;//所属跟单编码
	
	private String seller; //所属业务
	
	//@Column(name="seller_code")
	@Transient
	@Deprecated
	private String sellerCode; //所属业务编码
	
	@Column(name="client_brand")
	private String clientBrand; //	客户铭牌

	@Column(name="client_name")
	private String clientName;

	@Column(name="client_name_code")
	private String clientNameCode;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_delivery")
	private Date dateDelivery;

	@Temporal(TemporalType.DATE)
	@Column(name="date_examine")
	private Date dateExamine;

	@Temporal(TemporalType.DATE)
	@Column(name="date_order")
	private Date dateOrder; //下单时间

	private String description;

	private String district;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="order_spare")
	private String orderSpare;

	@ManyToOne(fetch = FetchType.LAZY)//参考机构表，向上看则是多对1；向下看则是1对多（可以考虑在写个Set<OrderInfo>）
	@JoinColumn(name="orderid_previous")
	private OrderInfo orderInfo;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="orderid_previous", updatable=false, insertable=false)
	private Integer pid;//父id
	
	private String payment;

	@Column(name="place_delivery")
	private String placeDelivery;

	@Column(name="product_client")
	private String productClient;

	@Column(name="product_factory")
	private String productFactory;

	private Integer quantity;

	private Integer state;
	
	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	//bi-directional many-to-one association to BomInfo
	@OneToMany(mappedBy="orderInfo") //@OneToOne会导致快加载问题
	//@Cascade(value = {CascadeType.DELETE }) //会级联删除
	private Set<BomInfo> bomInfos; //bom表单关联

	//bi-directional many-to-one association to BomInfo
	@OneToMany(mappedBy="orderInfo") //@OneToOne会导致快加载问题
	//@Cascade(value = {CascadeType.DELETE }) //会级联删除
	private Set<Manufacture> manufactures; //bom表单关联
	
	@OneToMany(mappedBy="orderInfo") //@OneToOne会导致快加载问题
	//@Cascade(value = {CascadeType.DELETE }) //会级联删除
	private Set<MaterialFollowup> materialFollowups; //物料跟进
	
	//bi-directional many-to-one association to Document
	@OneToMany(mappedBy="orderInfo")
	//@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE }) //会级联删除
	@OrderBy("uploadtime desc") //得加上@OrderBy()注解 dao的hql才能控制排序，否则只能用list
	private Set<Document> documents; //文档

	//bi-directional many-to-one association to OrderAltinfo
	@OneToMany(mappedBy="orderInfo", fetch=FetchType.LAZY)
	@Cascade(value = {CascadeType.DELETE}) //会级联增删改
	private Set<OrderInfoAlt> orderAltinfos; //变更记录

	//bi-directional many-to-one association to OrderFitting
	@OneToMany(mappedBy="orderInfo", fetch=FetchType.LAZY)
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy
	private Set<OrderFitting> orderFittings; //配件

	//bi-directional many-to-one association to OrderFollowup
	@OneToMany(mappedBy="orderInfo", fetch=FetchType.LAZY)
	//@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE }) //会级联删除
	private Set<OrderFollowup> orderFollowups; //跟单

	//bi-directional many-to-one association to OrderO
	@OneToMany(mappedBy="orderInfo", fetch=FetchType.LAZY)
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy
	private Set<OrderOS> orderOs; //软件信息

	//bi-directional many-to-one association to OrderHardware
	@OneToMany(mappedBy="orderInfo", fetch=FetchType.LAZY)
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy
	private Set<OrderHardware> orderHardwares;//硬件信息 
	
	//bi-directional many-to-one association to OrderPacking
	@OneToMany(mappedBy="orderInfo", fetch=FetchType.LAZY)
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@OrderBy
	private Set<OrderPacking> orderPackings; //包材

	//bi-directional many-to-one association to OrderShell
	@OneToMany(mappedBy="orderInfo", fetch=FetchType.LAZY)
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改，可以级联增加子表, CascadeType.DELETE_ORPHAN 支持实现一对多级联删除，但是已经废弃
	@OrderBy
	private Set<OrderShell> orderShells; //外壳
	
	//bi-directional many-to-one association to SalePo
	@OneToMany(mappedBy="orderInfo")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改，可以级联增加子表, CascadeType.DELETE_ORPHAN 支持实现一对多级联删除，但是已经废弃
	private Set<SalePo> salePos;
	
	@OneToMany(mappedBy="orderInfo")
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改，可以级联增加子表, CascadeType.DELETE_ORPHAN 支持实现一对多级联删除，但是已经废弃
	private Set<SaleInfo> saleInfo;
	
	//bi-directional many-to-one association to OrderPrototype
	@OneToMany(mappedBy="orderInfo")
	private Set<OrderPrototype> orderPrototypes;

/*	@OneToMany(mappedBy="orderId")
	private Set<OrderUnread> orderRead;*/
	
	//bi-directional many-to-one association to OrderRecord
	@OneToMany(mappedBy="orderInfo")
	private Set<OrderRecord> orderRecords;
	
	public OrderInfo() {
	}

	public OrderInfo(Integer id, String orderNo, OrderInfo orderInfo,
			Set<Document> documents) {
		super();
		this.id = id;
		this.orderNo = orderNo;
		this.orderInfo = orderInfo;
		this.documents = documents;
	}
	public OrderInfo(Integer id, String orderNo) {
		super();
		this.id = id;
		this.orderNo = orderNo;
	}

	public OrderInfo(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<SalePo> getSalePos() {
		return salePos;
	}

	public void setSalePos(Set<SalePo> salePos) {
		this.salePos = salePos;
	}

	public Set<OrderPrototype> getOrderPrototypes() {
		return orderPrototypes;
	}

	public void setOrderPrototypes(Set<OrderPrototype> orderPrototypes) {
		this.orderPrototypes = orderPrototypes;
	}

	public Set<OrderRecord> getOrderRecords() {
		return orderRecords;
	}

	public void setOrderRecords(Set<OrderRecord> orderRecords) {
		this.orderRecords = orderRecords;
	}

	public Set<MaterialFollowup> getMaterialFollowups() {
		return materialFollowups;
	}

	public void setMaterialFollowups(Set<MaterialFollowup> materialFollowups) {
		this.materialFollowups = materialFollowups;
	}

	public String getClientName() {
		return this.clientName;
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

	public String getMerchandiserCode() {
		return merchandiserCode;
	}

	public void setMerchandiserCode(String merchandiserCode) {
		this.merchandiserCode = merchandiserCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientNameCode() {
		return this.clientNameCode;
	}

	public void setClientNameCode(String clientNameCode) {
		this.clientNameCode = clientNameCode;
	}

	public Date getDateDelivery() {
		return this.dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public Date getDateExamine() {
		return this.dateExamine;
	}

	public void setDateExamine(Date dateExamine) {
		this.dateExamine = dateExamine;
	}

	public Date getDateOrder() {
		return this.dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderSpare() {
		return this.orderSpare;
	}

	public void setOrderSpare(String orderSpare) {
		this.orderSpare = orderSpare;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPlaceDelivery() {
		return this.placeDelivery;
	}

	public void setPlaceDelivery(String placeDelivery) {
		this.placeDelivery = placeDelivery;
	}

	public String getProductClient() {
		return this.productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

	public String getProductFactory() {
		return this.productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public Document addDocument(Document document) {
		getDocuments().add(document);
		document.setOrderInfo(this);

		return document;
	}

	public Document removeDocument(Document document) {
		getDocuments().remove(document);
		document.setOrderInfo(null);

		return document;
	}

	public Set<OrderInfoAlt> getOrderAltinfos() {
		return this.orderAltinfos;
	}

	public void setOrderAltinfos(Set<OrderInfoAlt> orderAltinfos) {
		this.orderAltinfos = orderAltinfos;
	}

	public OrderInfoAlt addOrderAltinfo(OrderInfoAlt orderAltinfo) {
		getOrderAltinfos().add(orderAltinfo);
		orderAltinfo.setOrderInfo(this);

		return orderAltinfo;
	}

	public OrderInfoAlt removeOrderAltinfo(OrderInfoAlt orderAltinfo) {
		getOrderAltinfos().remove(orderAltinfo);
		orderAltinfo.setOrderInfo(null);

		return orderAltinfo;
	}

	public Set<OrderFitting> getOrderFittings() {
		return this.orderFittings;
	}

	public void setOrderFittings(Set<OrderFitting> orderFittings) {
		this.orderFittings = orderFittings;
	}

	public OrderFitting addOrderFitting(OrderFitting orderFitting) {
		getOrderFittings().add(orderFitting);
		orderFitting.setOrderInfo(this);

		return orderFitting;
	}

	public OrderFitting removeOrderFitting(OrderFitting orderFitting) {
		getOrderFittings().remove(orderFitting);
		orderFitting.setOrderInfo(null);

		return orderFitting;
	}

	public Set<BomInfo> getBomInfos() {
		return bomInfos;
	}

	public void setBomInfos(Set<BomInfo> bomInfos) {
		this.bomInfos = bomInfos;
	}

	public Set<OrderFollowup> getOrderFollowups() {
		return orderFollowups;
	}

	public void setOrderFollowups(Set<OrderFollowup> orderFollowups) {
		this.orderFollowups = orderFollowups;
	}

	public Set<OrderHardware> getOrderHardwares() {
		return this.orderHardwares;
	}

	public void setOrderHardwares(Set<OrderHardware> orderHardwares) {
		this.orderHardwares = orderHardwares;
	}

	public OrderHardware addOrderHardware(OrderHardware orderHardware) {
		getOrderHardwares().add(orderHardware);
		orderHardware.setOrderInfo(this);

		return orderHardware;
	}

	public OrderHardware removeOrderHardware(OrderHardware orderHardware) {
		getOrderHardwares().remove(orderHardware);
		orderHardware.setOrderInfo(null);

		return orderHardware;
	}

	public Set<OrderOS> getOrderOs() {
		return orderOs;
	}

	public void setOrderOs(Set<OrderOS> orderOs) {
		this.orderOs = orderOs;
	}

	public Set<OrderPacking> getOrderPackings() {
		return this.orderPackings;
	}

	public void setOrderPackings(Set<OrderPacking> orderPackings) {
		this.orderPackings = orderPackings;
	}

	public OrderPacking addOrderPacking(OrderPacking orderPacking) {
		getOrderPackings().add(orderPacking);
		orderPacking.setOrderInfo(this);

		return orderPacking;
	}

	public OrderPacking removeOrderPacking(OrderPacking orderPacking) {
		getOrderPackings().remove(orderPacking);
		orderPacking.setOrderInfo(null);

		return orderPacking;
	}

	public Set<OrderShell> getOrderShells() {
		return this.orderShells;
	}

	public void setOrderShells(Set<OrderShell> orderShells) {
		this.orderShells = orderShells;
	}

	public OrderShell addOrderShell(OrderShell orderShell) {
		getOrderShells().add(orderShell);
		orderShell.setOrderInfo(this);

		return orderShell;
	}

	public OrderShell removeOrderShell(OrderShell orderShell) {
		getOrderShells().remove(orderShell);
		orderShell.setOrderInfo(null);

		return orderShell;
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

	/**
	 * 仅提供查询用
	 * @param seller
	 */
	public void setSeller(String seller) {
		this.seller = seller;
	}

	/**
	 * 获取本订单是否已经被读情况
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
	
	public String getClientBrand() {
		return clientBrand;
	}

	public void setClientBrand(String clientBrand) {
		this.clientBrand = clientBrand;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * 禁止使用，仅供hibernate查询注入使用
	 * @param orderRead
	 */
/*	@Deprecated
	public void setOrderRead(Set<OrderUnread> orderRead) {
		this.orderRead = orderRead;
	}*/
	
	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", merchandiserId=" + merchandiserId
				+ ", sellerId=" + sellerId + ", merchandiser=" + merchandiser
				+ ", seller="
				+ seller + ", clientName="
				+ clientName + ", clientNameCode=" + clientNameCode
				+ ", dateDelivery=" + dateDelivery + ", dateExamine="
				+ dateExamine + ", dateOrder=" + dateOrder + ", description="
				+ description + ", district=" + district + ", orderNo="
				+ orderNo + ", payment=" + payment + ", placeDelivery="
				+ placeDelivery + ", productClient=" + productClient
				+ ", productFactory=" + productFactory + ", quantity="
				+ quantity + ", state=" + state + ", timestamp=" + timestamp
				+ ", updatetime=" + updatetime + "]";
	}

	public Date getDateOrderStart() {
		return dateOrderStart;
	}

	public void setDateOrderStart(Date dateOrderStart) {
		this.dateOrderStart = dateOrderStart;
	}

	public Date getDateOrderEnd() {
		return dateOrderEnd;
	}

	public void setDateOrderEnd(Date dateOrderEnd) {
		this.dateOrderEnd = dateOrderEnd;
	}

	public Set<SaleInfo> getSaleInfo() {
		return saleInfo;
	}

	public void setSaleInfo(Set<SaleInfo> saleInfo) {
		this.saleInfo = saleInfo;
	}

	public String getDateOrderMonth() {
		return dateOrderMonth;
	}

	public void setDateOrderMonth(String dateOrderMonth) {
		this.dateOrderMonth = dateOrderMonth;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Set<Manufacture> getManufactures() {
		return manufactures;
	}

	public void setManufactures(Set<Manufacture> manufactures) {
		this.manufactures = manufactures;
	}
	
}