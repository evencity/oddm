package com.apical.oddm.facade.order.command;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.context.annotation.Description;


/**
 * The persistent class for the order_followup database table.
 * 
 */
public class OrderFollowupCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	//以下用于条件分页
	private String orderNo;
	
	private String clientName;
	
	private String productClient;
	
	private Integer agency;//代付

	private Integer bootLogo;//开机画面

	private Integer carton;//卡通箱

	private Integer colorbox;//彩盒

	private Date dateClient;//客户交期

	private Integer fitting;//配件

	private Integer hardware;//机身硬件

	private Integer inspection;//验货

	private Integer map;//地图

	private Integer membrane;//贴膜类

	private String merchandiser;//跟单员

	private String merchandiserCode;//跟单员编号

	private Integer packing;//包装袋

	private Integer payment;//付款

	private String plan;//方案

	private Integer preFile;//预存文件

	private String seller;//销售员

	private Integer shell;//机身外壳

	private Integer sorfware;//升级软件

	private Integer specification;//说明书和卡类

	private Integer state;//状态

	private Integer tags;//贴纸类

	private Integer uuid;//UUID

	private Timestamp timestamp;//创建时间

	private Date dateFactory;//工厂交期

	private Date dateShipment;//出货日期

	private String description;//描述
	
	private String status;//状态变更
	
	private Integer orderId;
	
	private String statusAbnormal;

	private String level;

	private String dutyOfficer;
	
	private String solution;
	
	private String  shipment;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProductClient() {
		return productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

	@Description(value="代付")
	public Integer getAgency() {
		return agency;
	}

	public void setAgency(Integer agency) {
		this.agency = agency;
	}
	
	@Description(value="开机画面")
	public Integer getBootLogo() {
		return bootLogo;
	}

	public void setBootLogo(Integer bootLogo) {
		this.bootLogo = bootLogo;
	}

	@Description(value="卡通箱")
	public Integer getCarton() {
		return carton;
	}

	public void setCarton(Integer carton) {
		this.carton = carton;
	}

	@Description(value="彩盒")
	public Integer getColorbox() {
		return colorbox;
	}

	public void setColorbox(Integer colorbox) {
		this.colorbox = colorbox;
	}

	@Description(value="客户交期")
	public Date getDateClient() {
		return dateClient;
	}

	public void setDateClient(Date dateClient) {
		this.dateClient = dateClient;
	}

	@Description(value="配件")
	public Integer getFitting() {
		return fitting;
	}

	public void setFitting(Integer fitting) {
		this.fitting = fitting;
	}

	@Description(value="机身硬件")
	public Integer getHardware() {
		return hardware;
	}

	public void setHardware(Integer hardware) {
		this.hardware = hardware;
	}

	@Description(value="验货")
	public Integer getInspection() {
		return inspection;
	}

	public void setInspection(Integer inspection) {
		this.inspection = inspection;
	}

	@Description(value="地图")
	public Integer getMap() {
		return map;
	}

	public void setMap(Integer map) {
		this.map = map;
	}

	@Description(value="贴膜类")
	public Integer getMembrane() {
		return membrane;
	}

	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}

	public String getMerchandiser() {
		return merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	public String getMerchandiserCode() {
		return merchandiserCode;
	}

	public void setMerchandiserCode(String merchandiserCode) {
		this.merchandiserCode = merchandiserCode;
	}

	@Description(value="包装袋")
	public Integer getPacking() {
		return packing;
	}

	public void setPacking(Integer packing) {
		this.packing = packing;
	}

	@Description(value="付款")
	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	@Description(value="方案")
	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	@Description(value="预存文件")
	public Integer getPreFile() {
		return preFile;
	}

	public void setPreFile(Integer preFile) {
		this.preFile = preFile;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	@Description(value="机身外壳")
	public Integer getShell() {
		return shell;
	}

	public void setShell(Integer shell) {
		this.shell = shell;
	}

	@Description(value="升级软件")
	public Integer getSorfware() {
		return sorfware;
	}

	public void setSorfware(Integer sorfware) {
		this.sorfware = sorfware;
	}

	@Description(value="说明书和卡类")
	public Integer getSpecification() {
		return specification;
	}

	public void setSpecification(Integer specification) {
		this.specification = specification;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Description(value="贴纸类")
	public Integer getTags() {
		return tags;
	}

	public void setTags(Integer tags) {
		this.tags = tags;
	}

	@Description(value="UUID号/其他")
	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Description(value="工厂交期")
	public Date getDateFactory() {
		return dateFactory;
	}

	public void setDateFactory(Date dateFactory) {
		this.dateFactory = dateFactory;
	}

	//@Description(value="最近出货日期") //不需要变更记录
	public Date getDateShipment() {
		return dateShipment;
	}

	public void setDateShipment(Date dateShipment) {
		this.dateShipment = dateShipment;
	}

	@Description(value="备注")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(value="状态更新")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderFollowupCommand [id=" + id + ", orderNo=" + orderNo + ", clientName=" + clientName + ", productClient=" + productClient + ", agency=" + agency + ", bootLogo="
				+ bootLogo + ", carton=" + carton + ", colorbox=" + colorbox + ", dateClient=" + dateClient + ", fitting=" + fitting + ", hardware=" + hardware + ", inspection="
				+ inspection + ", map=" + map + ", membrane=" + membrane + ", merchandiser=" + merchandiser + ", merchandiserCode=" + merchandiserCode + ", packing=" + packing
				+ ", payment=" + payment + ", plan=" + plan + ", preFile=" + preFile + ", seller=" + seller + ", shell=" + shell + ", sorfware=" + sorfware + ", specification="
				+ specification + ", state=" + state + ", tags=" + tags + ", uuid=" + uuid + ", timestamp=" + timestamp + ", dateFactory=" + dateFactory + ", dateShipment="
				+ dateShipment + ", description=" + description + ", status=" + status + ", orderId=" + orderId
				+ "]";
	}

}