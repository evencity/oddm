package com.apical.oddm.facade.bom.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Description;

import com.apical.oddm.facade.order.dto.OrderInfoDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2017年2月9日 下午2:04:26 
 * @version 1.0 
 */

public class BomDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer unread; //订单1未读，供传查询用，页面只显示1未读即可
	
	private String brand;//品牌

	private String description;

	private String materialCode;

	private String productName; //品名

	private String specification;

	private Integer state;

	private Timestamp timestamp;

	private String title;

	private Timestamp updatetime;

	private String maker;
	
	private Date date; //日期
	private String dateString; //日期
	
	private String version;

	private OrderInfoDTO orderInfoDTO;

	private List<BomMaterialRefDTO> bomMaterialRefDTOs;

	private List<BomInfoAltDTO> bomInfoAltDTOs;
	
	private Integer orderId;
	
	private String orderNo;
	
	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUnread() {
		return unread;
	}

	public void setUnread(Integer unread) {
		this.unread = unread;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	@Description(value="描述")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Description(value="物料编码")
	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@Description(value="品名")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Description(value="规格型号与封装")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	@Description(value="制表人")
	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}
	@Description(value="版本")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public OrderInfoDTO getOrderInfoDTO() {
		return orderInfoDTO;
	}

	public void setOrderInfoDTO(OrderInfoDTO orderInfoDTO) {
		this.orderInfoDTO = orderInfoDTO;
	}


	public List<BomMaterialRefDTO> getBomMaterialRefDTOs() {
		return bomMaterialRefDTOs;
	}

	public void setBomMaterialRefDTOs(List<BomMaterialRefDTO> bomMaterialRefDTOs) {
		this.bomMaterialRefDTOs = bomMaterialRefDTOs;
	}

	public List<BomInfoAltDTO> getBomInfoAltDTOs() {
		return bomInfoAltDTOs;
	}

	public void setBomInfoAltDTOs(List<BomInfoAltDTO> bomInfoAltDTOs) {
		this.bomInfoAltDTOs = bomInfoAltDTOs;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	@Description(value="日期")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "BomDTO [id=" + id + ", unread=" + unread + ", brand=" + brand + ", description=" + description + ", materialCode=" + materialCode + ", productName=" + productName
				+ ", specification=" + specification + ", state=" + state + ", timestamp=" + timestamp + ", title=" + title + ", updatetime=" + updatetime + ", maker=" + maker
				+ ", version=" + version + ", orderInfoDTO=" + orderInfoDTO + ", bomMaterialRefDTOs="
				+ (bomMaterialRefDTOs != null ? bomMaterialRefDTOs.subList(0, Math.min(bomMaterialRefDTOs.size(), maxLen)) : null) + ", bomInfoAltDTOs="
				+ (bomInfoAltDTOs != null ? bomInfoAltDTOs.subList(0, Math.min(bomInfoAltDTOs.size(), maxLen)) : null) + ", orderId=" + orderId + ", orderNo=" + orderNo + "]";
	}

}
