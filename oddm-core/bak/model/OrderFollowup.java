package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


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
	private int id;

	private byte agency;

	@Column(name="boot_logo")
	private byte bootLogo;

	private byte carton;

	private byte colorbox;

	@Temporal(TemporalType.DATE)
	@Column(name="date_client")
	private Date dateClient;

	private byte fitting;

	private byte hardware;

	private byte inspection;

	private byte map;

	private byte membrane;

	private String merchandiser;

	@Column(name="merchandiser_code")
	private String merchandiserCode;

	private byte packing;

	private byte payment;

	private String plan;

	@Column(name="pre_file")
	private byte preFile;

	private String seller;

	private byte shell;

	private byte sorfware;

	private byte specification;

	private byte state;

	private byte tags;

	private byte uuid;

	//bi-directional many-to-one association to OrderInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;

	//bi-directional many-to-one association to OrderFollowupAlter
	@OneToMany(mappedBy="orderFollowup")
	private Set<OrderFollowupAlter> orderFollowupAlters;

	public OrderFollowup() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getAgency() {
		return this.agency;
	}

	public void setAgency(byte agency) {
		this.agency = agency;
	}

	public byte getBootLogo() {
		return this.bootLogo;
	}

	public void setBootLogo(byte bootLogo) {
		this.bootLogo = bootLogo;
	}

	public byte getCarton() {
		return this.carton;
	}

	public void setCarton(byte carton) {
		this.carton = carton;
	}

	public byte getColorbox() {
		return this.colorbox;
	}

	public void setColorbox(byte colorbox) {
		this.colorbox = colorbox;
	}

	public Date getDateClient() {
		return this.dateClient;
	}

	public void setDateClient(Date dateClient) {
		this.dateClient = dateClient;
	}

	public byte getFitting() {
		return this.fitting;
	}

	public void setFitting(byte fitting) {
		this.fitting = fitting;
	}

	public byte getHardware() {
		return this.hardware;
	}

	public void setHardware(byte hardware) {
		this.hardware = hardware;
	}

	public byte getInspection() {
		return this.inspection;
	}

	public void setInspection(byte inspection) {
		this.inspection = inspection;
	}

	public byte getMap() {
		return this.map;
	}

	public void setMap(byte map) {
		this.map = map;
	}

	public byte getMembrane() {
		return this.membrane;
	}

	public void setMembrane(byte membrane) {
		this.membrane = membrane;
	}

	public String getMerchandiser() {
		return this.merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	public String getMerchandiserCode() {
		return this.merchandiserCode;
	}

	public void setMerchandiserCode(String merchandiserCode) {
		this.merchandiserCode = merchandiserCode;
	}

	public byte getPacking() {
		return this.packing;
	}

	public void setPacking(byte packing) {
		this.packing = packing;
	}

	public byte getPayment() {
		return this.payment;
	}

	public void setPayment(byte payment) {
		this.payment = payment;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public byte getPreFile() {
		return this.preFile;
	}

	public void setPreFile(byte preFile) {
		this.preFile = preFile;
	}

	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public byte getShell() {
		return this.shell;
	}

	public void setShell(byte shell) {
		this.shell = shell;
	}

	public byte getSorfware() {
		return this.sorfware;
	}

	public void setSorfware(byte sorfware) {
		this.sorfware = sorfware;
	}

	public byte getSpecification() {
		return this.specification;
	}

	public void setSpecification(byte specification) {
		this.specification = specification;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public byte getTags() {
		return this.tags;
	}

	public void setTags(byte tags) {
		this.tags = tags;
	}

	public byte getUuid() {
		return this.uuid;
	}

	public void setUuid(byte uuid) {
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

}