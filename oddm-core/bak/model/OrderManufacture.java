package com.apical.oddm.core.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the order_manufacture database table.
 * 
 */
@Entity
@Table(name="order_manufacture")
@NamedQuery(name="OrderManufacture.findAll", query="SELECT o FROM OrderManufacture o")
public class OrderManufacture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String approver;

	private String auditor;

	@Column(name="batch_no")
	private String batchNo;

	@Column(name="client_name_code")
	private String clientNameCode;

	@Temporal(TemporalType.DATE)
	@Column(name="date_issue")
	private Date dateIssue;

	@Temporal(TemporalType.DATE)
	@Column(name="date_shipment")
	private Date dateShipment;

	private String drafter;

	@Column(name="material_code")
	private String materialCode;

	@Column(name="name_project")
	private String nameProject;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="product_client")
	private String productClient;

	@Column(name="product_factory")
	private String productFactory;

	@Column(name="product_name")
	private String productName;

	private int quantity;

	private String remark;

	private String specification;

	private byte state;

	private Timestamp timestamp;

	private Timestamp updatetime;

	//bi-directional many-to-one association to OrderMftCheck
	@OneToMany(mappedBy="orderManufacture")
	private Set<OrderMftCheck> orderMftChecks;

	//bi-directional many-to-one association to OrderMftMaterial
	@OneToMany(mappedBy="orderManufacture")
	private Set<OrderMftMaterial> orderMftMaterials;

	//bi-directional many-to-one association to OrderMftO
	@OneToMany(mappedBy="orderManufacture")
	private Set<OrderMftO> orderMftOs;

	//bi-directional many-to-one association to OrderMftPic
	@OneToMany(mappedBy="orderManufacture")
	private Set<OrderMftPic> orderMftPics;

	public OrderManufacture() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getAuditor() {
		return this.auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getClientNameCode() {
		return this.clientNameCode;
	}

	public void setClientNameCode(String clientNameCode) {
		this.clientNameCode = clientNameCode;
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

	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getNameProject() {
		return this.nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
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

	public Set<OrderMftCheck> getOrderMftChecks() {
		return this.orderMftChecks;
	}

	public void setOrderMftChecks(Set<OrderMftCheck> orderMftChecks) {
		this.orderMftChecks = orderMftChecks;
	}

	public OrderMftCheck addOrderMftCheck(OrderMftCheck orderMftCheck) {
		getOrderMftChecks().add(orderMftCheck);
		orderMftCheck.setOrderManufacture(this);

		return orderMftCheck;
	}

	public OrderMftCheck removeOrderMftCheck(OrderMftCheck orderMftCheck) {
		getOrderMftChecks().remove(orderMftCheck);
		orderMftCheck.setOrderManufacture(null);

		return orderMftCheck;
	}

	public Set<OrderMftMaterial> getOrderMftMaterials() {
		return this.orderMftMaterials;
	}

	public void setOrderMftMaterials(Set<OrderMftMaterial> orderMftMaterials) {
		this.orderMftMaterials = orderMftMaterials;
	}

	public OrderMftMaterial addOrderMftMaterial(OrderMftMaterial orderMftMaterial) {
		getOrderMftMaterials().add(orderMftMaterial);
		orderMftMaterial.setOrderManufacture(this);

		return orderMftMaterial;
	}

	public OrderMftMaterial removeOrderMftMaterial(OrderMftMaterial orderMftMaterial) {
		getOrderMftMaterials().remove(orderMftMaterial);
		orderMftMaterial.setOrderManufacture(null);

		return orderMftMaterial;
	}

	public Set<OrderMftO> getOrderMftOs() {
		return this.orderMftOs;
	}

	public void setOrderMftOs(Set<OrderMftO> orderMftOs) {
		this.orderMftOs = orderMftOs;
	}

	public OrderMftO addOrderMftO(OrderMftO orderMftO) {
		getOrderMftOs().add(orderMftO);
		orderMftO.setOrderManufacture(this);

		return orderMftO;
	}

	public OrderMftO removeOrderMftO(OrderMftO orderMftO) {
		getOrderMftOs().remove(orderMftO);
		orderMftO.setOrderManufacture(null);

		return orderMftO;
	}

	public Set<OrderMftPic> getOrderMftPics() {
		return this.orderMftPics;
	}

	public void setOrderMftPics(Set<OrderMftPic> orderMftPics) {
		this.orderMftPics = orderMftPics;
	}

	public OrderMftPic addOrderMftPic(OrderMftPic orderMftPic) {
		getOrderMftPics().add(orderMftPic);
		orderMftPic.setOrderManufacture(this);

		return orderMftPic;
	}

	public OrderMftPic removeOrderMftPic(OrderMftPic orderMftPic) {
		getOrderMftPics().remove(orderMftPic);
		orderMftPic.setOrderManufacture(null);

		return orderMftPic;
	}

}