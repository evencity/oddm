package com.apical.oddm.facade.order.dto;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the order_followup database table.
 * 
 */
public class OrderFollowupDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;

	private Integer agency;

	private Integer bootLogo;

	private Integer carton;

	private Integer colorbox;

	private String dateClient;

	private String status;
	
	private Integer fitting;

	private Integer hardware;

	private Integer inspection;

	private Integer map;

	private Integer membrane;

	private String merchandiser;

	private String merchandiserCode;

	private Integer packing;

	private Integer payment;

	private String plan;

	private Integer preFile;

	private String seller;

	private Integer shell;

	private Integer sorfware;

	private Integer specification;

	private Integer state;

	private Integer tags;

	private Integer uuid;

	private Timestamp timestamp;

	private String dateFactory;

	private String dateShipment;

	private String description;
	
	private Integer shipmentNo;
	
	private String updatetime;
	
	private OrderInfoDTO orderInfoDTO;
	
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

	public OrderInfoDTO getOrderInfoDTO() {
		return orderInfoDTO;
	}

	public void setOrderInfoDTO(OrderInfoDTO orderInfoDTO) {
		this.orderInfoDTO = orderInfoDTO;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgency() {
		return agency;
	}

	public void setAgency(Integer agency) {
		this.agency = agency;
	}

	public Integer getBootLogo() {
		return bootLogo;
	}

	public void setBootLogo(Integer bootLogo) {
		this.bootLogo = bootLogo;
	}

	public Integer getCarton() {
		return carton;
	}

	public void setCarton(Integer carton) {
		this.carton = carton;
	}

	public Integer getColorbox() {
		return colorbox;
	}

	public void setColorbox(Integer colorbox) {
		this.colorbox = colorbox;
	}

	public String getDateClient() {
		return dateClient;
	}

	public void setDateClient(String dateClient) {
		this.dateClient = dateClient;
	}

	public Integer getFitting() {
		return fitting;
	}

	public void setFitting(Integer fitting) {
		this.fitting = fitting;
	}

	public Integer getHardware() {
		return hardware;
	}

	public void setHardware(Integer hardware) {
		this.hardware = hardware;
	}

	public Integer getInspection() {
		return inspection;
	}

	public void setInspection(Integer inspection) {
		this.inspection = inspection;
	}

	public Integer getMap() {
		return map;
	}

	public void setMap(Integer map) {
		this.map = map;
	}

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

	public Integer getPacking() {
		return packing;
	}

	public void setPacking(Integer packing) {
		this.packing = packing;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

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

	public Integer getShell() {
		return shell;
	}

	public void setShell(Integer shell) {
		this.shell = shell;
	}

	public Integer getSorfware() {
		return sorfware;
	}

	public void setSorfware(Integer sorfware) {
		this.sorfware = sorfware;
	}

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

	public Integer getTags() {
		return tags;
	}

	public void setTags(Integer tags) {
		this.tags = tags;
	}

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

	public String getDateFactory() {
		return dateFactory;
	}

	public void setDateFactory(String dateFactory) {
		this.dateFactory = dateFactory;
	}

	public String getDateShipment() {
		return dateShipment;
	}

	public void setDateShipment(String dateShipment) {
		this.dateShipment = dateShipment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(Integer shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderFollowupDTO [id=" + id + ", agency=" + agency + ", bootLogo=" + bootLogo + ", carton=" + carton + ", colorbox=" + colorbox + ", dateClient=" + dateClient
				+ ", status=" + status + ", fitting=" + fitting + ", hardware=" + hardware + ", inspection=" + inspection + ", map=" + map + ", membrane=" + membrane
				+ ", merchandiser=" + merchandiser + ", merchandiserCode=" + merchandiserCode + ", packing=" + packing + ", payment=" + payment + ", plan=" + plan + ", preFile="
				+ preFile + ", seller=" + seller + ", shell=" + shell + ", sorfware=" + sorfware + ", specification=" + specification + ", state=" + state + ", tags=" + tags
				+ ", uuid=" + uuid + ", timestamp=" + timestamp + ", dateFactory=" + dateFactory + ", dateShipment=" + dateShipment + ", description=" + description
				+ ", shipmentNo=" + shipmentNo + ", orderInfoDTO=" + orderInfoDTO + "]";
	}

}