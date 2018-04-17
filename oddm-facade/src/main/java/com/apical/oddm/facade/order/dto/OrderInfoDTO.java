package com.apical.oddm.facade.order.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.apical.oddm.facade.document.dto.DocumentDTO;


/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月31日 上午9:43:12 
 * @version 1.0 
 */

public class OrderInfoDTO implements Serializable{


	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer isRead; 
	
	private Integer sellerId; //业务id
	
	private String seller; //所属业务
	
	private Integer merchandiserId; //跟单id

	private String merchandiser;//所属跟单

	private String clientBrand;// 客户品牌

	private String clientName; //客户名称

	private String clientNameCode;//客户名称编码
	
	private String dateDelivery;//交货日期

	private String dateExamine;//验货日期

	private String dateOrder;//下单日期

	private String description;//备注说明

	private String district;//国家地区

	private String orderNo;//定单号

	private String orderSpare;//定单备注
	
	private Integer pid;//上一个单号

	private String payment;//付款方式

	private String placeDelivery;//交货地点

	private String productClient;//客户型号

	private String productFactory;//工厂机型

	private Integer quantity;//定单数量

	private Integer state;//定单状态
	
	private String updatetime;//更新时间
	
	private String timestamp;
	
	private OrderOSDTO orderOSDTO;//软件信息
	
	private OrderInfoDTO orderInfoDTO;
	
	private Set<OrderHardwareDTO> orderHardwareDTOs = new TreeSet<OrderHardwareDTO>();//硬件信息
	
	private Set<OrderShellDTO> orderShellDTOs = new TreeSet<OrderShellDTO>();//外壳工艺
	
	private Set<OrderScreenDTO> orderScreenDTOs = new TreeSet<OrderScreenDTO>();//屏幕
	
	private Set<OrderPackingDTO> orderPackingDTOs = new TreeSet<OrderPackingDTO>();//包材
	
	private Set<OrderFittingDTO> orderFittingDTOs = new TreeSet<OrderFittingDTO>();//配件
	
	private Set<DocumentDTO> documentDTOs = new TreeSet<DocumentDTO>();

	/*********辅助字段*********/
	private String dateOrderWeek; //周
	private String productType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Integer getMerchandiserId() {
		return merchandiserId;
	}

	public void setMerchandiserId(Integer merchandiserId) {
		this.merchandiserId = merchandiserId;
	}

	public String getMerchandiser() {
		return merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	public String getClientBrand() {
		return clientBrand;
	}

	public void setClientBrand(String clientBrand) {
		this.clientBrand = clientBrand;
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

	public String getDateDelivery() {
		return dateDelivery;
	}

	public void setDateDelivery(String dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public String getDateExamine() {
		return dateExamine;
	}

	public void setDateExamine(String dateExamine) {
		this.dateExamine = dateExamine;
	}

	public String getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(String dateOrder) {
		this.dateOrder = dateOrder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderSpare() {
		return orderSpare;
	}

	public void setOrderSpare(String orderSpare) {
		this.orderSpare = orderSpare;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPlaceDelivery() {
		return placeDelivery;
	}

	public void setPlaceDelivery(String placeDelivery) {
		this.placeDelivery = placeDelivery;
	}

	public String getProductClient() {
		return productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

	public String getProductFactory() {
		return productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public OrderOSDTO getOrderOSDTO() {
		return orderOSDTO;
	}

	public void setOrderOSDTO(OrderOSDTO orderOSDTO) {
		this.orderOSDTO = orderOSDTO;
	}

	public OrderInfoDTO getOrderInfoDTO() {
		return orderInfoDTO;
	}

	public void setOrderInfoDTO(OrderInfoDTO orderInfoDTO) {
		this.orderInfoDTO = orderInfoDTO;
	}

	public Set<OrderHardwareDTO> getOrderHardwareDTOs() {
		return orderHardwareDTOs;
	}

	public void setOrderHardwareDTOs(Set<OrderHardwareDTO> orderHardwareDTOs) {
		this.orderHardwareDTOs = orderHardwareDTOs;
	}

	public Set<OrderShellDTO> getOrderShellDTOs() {
		return orderShellDTOs;
	}

	public void setOrderShellDTOs(Set<OrderShellDTO> orderShellDTOs) {
		this.orderShellDTOs = orderShellDTOs;
	}

	public Set<OrderScreenDTO> getOrderScreenDTOs() {
		return orderScreenDTOs;
	}

	public void setOrderScreenDTOs(Set<OrderScreenDTO> orderScreenDTOs) {
		this.orderScreenDTOs = orderScreenDTOs;
	}

	public Set<OrderPackingDTO> getOrderPackingDTOs() {
		return orderPackingDTOs;
	}

	public void setOrderPackingDTOs(Set<OrderPackingDTO> orderPackingDTOs) {
		this.orderPackingDTOs = orderPackingDTOs;
	}

	public Set<OrderFittingDTO> getOrderFittingDTOs() {
		return orderFittingDTOs;
	}

	public void setOrderFittingDTOs(Set<OrderFittingDTO> orderFittingDTOs) {
		this.orderFittingDTOs = orderFittingDTOs;
	}

	public Set<DocumentDTO> getDocumentDTOs() {
		return documentDTOs;
	}

	public void setDocumentDTOs(Set<DocumentDTO> documentDTOs) {
		this.documentDTOs = documentDTOs;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "OrderInfoDTO [id=" + id + ", isRead=" + isRead + ", sellerId=" + sellerId + ", seller=" + seller + ", merchandiserId=" + merchandiserId + ", merchandiser="
				+ merchandiser + ", clientBrand=" + clientBrand + ", clientName=" + clientName + ", clientNameCode=" + clientNameCode + ", dateDelivery=" + dateDelivery
				+ ", dateExamine=" + dateExamine + ", dateOrder=" + dateOrder + ", description=" + description + ", district=" + district + ", orderNo=" + orderNo
				+ ", orderSpare=" + orderSpare + ", pid=" + pid + ", payment=" + payment + ", placeDelivery=" + placeDelivery + ", productClient=" + productClient
				+ ", productFactory=" + productFactory + ", quantity=" + quantity + ", state=" + state + ", updatetime=" + updatetime + ", timestamp=" + timestamp
				+ ", orderOSDTO=" + orderOSDTO + ", orderInfoDTO=" + orderInfoDTO + ", orderHardwareDTOs="
				+ (orderHardwareDTOs != null ? toString(orderHardwareDTOs, maxLen) : null) + ", orderShellDTOs="
				+ (orderShellDTOs != null ? toString(orderShellDTOs, maxLen) : null) + ", orderScreenDTOs=" + (orderScreenDTOs != null ? toString(orderScreenDTOs, maxLen) : null)
				+ ", orderPackingDTOs=" + (orderPackingDTOs != null ? toString(orderPackingDTOs, maxLen) : null) + ", orderFittingDTOs="
				+ (orderFittingDTOs != null ? toString(orderFittingDTOs, maxLen) : null) + ", documentDTOs=" + (documentDTOs != null ? toString(documentDTOs, maxLen) : null) + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	public String getDateOrderWeek() {
		return dateOrderWeek;
	}

	public void setDateOrderWeek(String dateOrderWeek) {
		this.dateOrderWeek = dateOrderWeek;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	
	

}
