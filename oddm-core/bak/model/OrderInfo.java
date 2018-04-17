package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the order_info database table.
 * 
 */
@Entity
@Table(name="order_info")
@NamedQuery(name="OrderInfo.findAll", query="SELECT o FROM OrderInfo o")
public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="biz_name")
	private String bizName;

	@Column(name="client_brand")
	private String clientBrand;

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
	private Date dateOrder;

	private String description;

	private String district;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="order_spare")
	private String orderSpare;

	@Column(name="orderid_previous")
	private int orderidPrevious;

	private String payment;

	@Column(name="place_delivery")
	private String placeDelivery;

	@Column(name="product_client")
	private String productClient;

	@Column(name="product_factory")
	private String productFactory;

	private int quantity;

	private byte state;

	private Timestamp timestamp;

	private Timestamp updatetime;

	//bi-directional many-to-one association to BomInfo
	@OneToMany(mappedBy="orderInfo")
	private Set<BomInfo> bomInfos;

	//bi-directional many-to-one association to Document
	@OneToMany(mappedBy="orderInfo")
	private Set<Document> documents;

	//bi-directional many-to-one association to OrderAltinfo
	@OneToMany(mappedBy="orderInfo")
	private Set<OrderAltinfo> orderAltinfos;

	//bi-directional many-to-one association to OrderFitting
	@OneToMany(mappedBy="orderInfo")
	private Set<OrderFitting> orderFittings;

	//bi-directional many-to-one association to OrderFollowup
	@OneToMany(mappedBy="orderInfo")
	private Set<OrderFollowup> orderFollowups;

	//bi-directional many-to-one association to OrderHardware
	@OneToMany(mappedBy="orderInfo")
	private Set<OrderHardware> orderHardwares;

	//bi-directional many-to-one association to OrderO
	@OneToMany(mappedBy="orderInfo")
	private Set<OrderOS> orderOs;

	//bi-directional many-to-one association to OrderPacking
	@OneToMany(mappedBy="orderInfo")
	private Set<OrderPacking> orderPackings;

	//bi-directional many-to-one association to OrderShell
	@OneToMany(mappedBy="orderInfo")
	private Set<OrderShell> orderShells;

	public OrderInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBizName() {
		return this.bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getClientBrand() {
		return this.clientBrand;
	}

	public void setClientBrand(String clientBrand) {
		this.clientBrand = clientBrand;
	}

	public String getClientName() {
		return this.clientName;
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

	public int getOrderidPrevious() {
		return this.orderidPrevious;
	}

	public void setOrderidPrevious(int orderidPrevious) {
		this.orderidPrevious = orderidPrevious;
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

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
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

	public Set<BomInfo> getBomInfos() {
		return this.bomInfos;
	}

	public void setBomInfos(Set<BomInfo> bomInfos) {
		this.bomInfos = bomInfos;
	}

	public BomInfo addBomInfo(BomInfo bomInfo) {
		getBomInfos().add(bomInfo);
		bomInfo.setOrderInfo(this);

		return bomInfo;
	}

	public BomInfo removeBomInfo(BomInfo bomInfo) {
		getBomInfos().remove(bomInfo);
		bomInfo.setOrderInfo(null);

		return bomInfo;
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

	public Set<OrderAltinfo> getOrderAltinfos() {
		return this.orderAltinfos;
	}

	public void setOrderAltinfos(Set<OrderAltinfo> orderAltinfos) {
		this.orderAltinfos = orderAltinfos;
	}

	public OrderAltinfo addOrderAltinfo(OrderAltinfo orderAltinfo) {
		getOrderAltinfos().add(orderAltinfo);
		orderAltinfo.setOrderInfo(this);

		return orderAltinfo;
	}

	public OrderAltinfo removeOrderAltinfo(OrderAltinfo orderAltinfo) {
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

	public Set<OrderFollowup> getOrderFollowups() {
		return this.orderFollowups;
	}

	public void setOrderFollowups(Set<OrderFollowup> orderFollowups) {
		this.orderFollowups = orderFollowups;
	}

	public OrderFollowup addOrderFollowup(OrderFollowup orderFollowup) {
		getOrderFollowups().add(orderFollowup);
		orderFollowup.setOrderInfo(this);

		return orderFollowup;
	}

	public OrderFollowup removeOrderFollowup(OrderFollowup orderFollowup) {
		getOrderFollowups().remove(orderFollowup);
		orderFollowup.setOrderInfo(null);

		return orderFollowup;
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
		return this.orderOs;
	}

	public void setOrderOs(Set<OrderOS> orderOs) {
		this.orderOs = orderOs;
	}

	public OrderOS addOrderO(OrderOS orderO) {
		getOrderOs().add(orderO);
		orderO.setOrderInfo(this);

		return orderO;
	}

	public OrderOS removeOrderO(OrderOS orderO) {
		getOrderOs().remove(orderO);
		orderO.setOrderInfo(null);

		return orderO;
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

}