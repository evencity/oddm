package com.apical.oddm.facade.manufacture.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManufactureDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String batchNo; //生产批号
	private String projectName; //项目名称
	private String dateShipment;//出货日期
	private String drafter;//拟稿
	private String auditor;//审核
	private String dateIssue;//发行日期
	private String approver;//批准
	private String remark;//备注
	private long materialCode;//物料编码
	private String productName;//品名
	private String specification;//规格型号及封装
	private String orderNo;//订单号
	private String clientName;//顾客名称及
	private String productFactory;//工厂机型
	private String productClient;//客户型号
	private Integer quantity;//生产数量
	private String timestamp;//创建时间
	private String updatetime;//更新时间
	private Integer state;//1-录入；2-完结
	private String notice;//注意事项
	private String version;//版本号
	private String picture;//存储图片路径
	private Integer orderId;

	private Integer unread; //1未读，供传查询用，页面只显示1未读即可
	
	private String manufacture;
	
	private ManufactureOsDTO manufactureOsDTO;
	
	private List<ManufactureFittingDTO> manufactureFittingDTOs = new ArrayList<ManufactureFittingDTO>();
	
	private List<ManufacturePackageDTO> manufacturePackageDTOs = new ArrayList<ManufacturePackageDTO>();
	
	private List<ManufactureShellDTO> manufactureShellDTOs = new ArrayList<ManufactureShellDTO>();
	
	public Integer getUnread() {
		return unread;
	}

	public void setUnread(Integer unread) {
		this.unread = unread;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDateShipment() {
		return dateShipment;
	}

	public void setDateShipment(String dateShipment) {
		this.dateShipment = dateShipment;
	}

	public String getDrafter() {
		return drafter;
	}

	public void setDrafter(String drafter) {
		this.drafter = drafter;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(String dateIssue) {
		this.dateIssue = dateIssue;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(long materialCode) {
		this.materialCode = materialCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getManufacture() {
		return manufacture;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	public ManufactureOsDTO getManufactureOsDTO() {
		return manufactureOsDTO;
	}

	public void setManufactureOsDTO(ManufactureOsDTO manufactureOsDTO) {
		this.manufactureOsDTO = manufactureOsDTO;
	}

	public List<ManufactureFittingDTO> getManufactureFittingDTOs() {
		return manufactureFittingDTOs;
	}

	public void setManufactureFittingDTOs(List<ManufactureFittingDTO> manufactureFittingDTOs) {
		this.manufactureFittingDTOs = manufactureFittingDTOs;
	}

	public List<ManufacturePackageDTO> getManufacturePackageDTOs() {
		return manufacturePackageDTOs;
	}

	public void setManufacturePackageDTOs(List<ManufacturePackageDTO> manufacturePackageDTOs) {
		this.manufacturePackageDTOs = manufacturePackageDTOs;
	}

	public List<ManufactureShellDTO> getManufactureShellDTOs() {
		return manufactureShellDTOs;
	}

	public void setManufactureShellDTOs(List<ManufactureShellDTO> manufactureShellDTOs) {
		this.manufactureShellDTOs = manufactureShellDTOs;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "ManufactureDTO [id=" + id + ", batchNo=" + batchNo + ", projectName=" + projectName + ", dateShipment=" + dateShipment + ", drafter=" + drafter + ", auditor="
				+ auditor + ", dateIssue=" + dateIssue + ", approver=" + approver + ", remark=" + remark + ", materialCode=" + materialCode + ", productName=" + productName
				+ ", specification=" + specification + ", orderNo=" + orderNo + ", clientName=" + clientName + ", productFactory=" + productFactory + ", productClient="
				+ productClient + ", quantity=" + quantity + ", timestamp=" + timestamp + ", updatetime=" + updatetime + ", state=" + state + ", notice=" + notice + ", version="
				+ version + ", picture=" + picture + ", manufacture=" + manufacture + ", manufactureOsDTO=" + manufactureOsDTO + ", manufactureFittingDTOs="
				+ (manufactureFittingDTOs != null ? manufactureFittingDTOs.subList(0, Math.min(manufactureFittingDTOs.size(), maxLen)) : null) + ", manufacturePackageDTOs="
				+ (manufacturePackageDTOs != null ? manufacturePackageDTOs.subList(0, Math.min(manufacturePackageDTOs.size(), maxLen)) : null) + ", manufactureShellDTOs="
				+ (manufactureShellDTOs != null ? manufactureShellDTOs.subList(0, Math.min(manufactureShellDTOs.size(), maxLen)) : null) + "]";
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}
