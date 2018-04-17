package com.apical.oddm.facade.order.command;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.annotation.Description;

import com.apical.oddm.facade.document.dto.DocumentDTO;
import com.apical.oddm.facade.materialFollow.command.MaterialFollowupCommand;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月31日 上午11:38:50 
 * @version 1.0 
 */

public class OrderInfoCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	//private String bizName;//业务名称

	//private String clientBrand;// 客户品牌
	
	private Integer isRead; //客户名称
	
	private String clientName; //客户名称
	
	private String clientBrand; //	客户铭牌

	private String clientNameCode;//客户名称编码
	
	private Date dateDelivery;//交货日期

	private Date dateExamine;//验货日期

	private Date dateOrder;//下单日期

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
	
	private Timestamp updatetime;//更新时间
	
	private Integer sellerId; //业务id
	
	private String seller; //所属业务
	
	private Integer merchandiserId; //跟单id

	private String merchandiser;//所属跟单
	
	private OrderOSCommand orderOSCommand;//软件信息
	
	private Set<OrderHardwareCommand> orderHardwareCommands = new TreeSet<OrderHardwareCommand>();//硬件信息
	
	private Set<OrderShellCommand> orderShellCommands = new TreeSet<OrderShellCommand>();//外壳工艺
	
	private Set<OrderPackingCommand> orderPackingCommands = new TreeSet<OrderPackingCommand>();//包材
	
	private Set<OrderFittingCommand> orderFittingCommands = new TreeSet<OrderFittingCommand>();//配件
	
	private Set<MaterialFollowupCommand> materialFollowupCommands; //物料跟进
	
	private Set<DocumentDTO> documentDTOs  = new TreeSet<DocumentDTO>(); //文档

	private String dateOrderStart;//下单时间，供传查询用
	
	private String dateOrderEnd;
	
	/********辅助字段*********/
	private String dateOrderMonth; //月份
	private String productType;

	public String getDateOrderStart() {
		return dateOrderStart;
	}

	public void setDateOrderStart(String dateOrderStart) {
		this.dateOrderStart = dateOrderStart;
	}

	public String getDateOrderEnd() {
		return dateOrderEnd;
	}

	public void setDateOrderEnd(String dateOrderEnd) {
		this.dateOrderEnd = dateOrderEnd;
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

	@Description(value="客户铭牌")
	public String getClientBrand() {
		return clientBrand;
	}

	public void setClientBrand(String clientBrand) {
		this.clientBrand = clientBrand;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	@Description(value="所属业务")
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

	@Description(value="所属跟单")
	public String getMerchandiser() {
		return merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	public Set<MaterialFollowupCommand> getMaterialFollowupCommands() {
		return materialFollowupCommands;
	}

	public void setMaterialFollowupCommands(Set<MaterialFollowupCommand> materialFollowupCommands) {
		this.materialFollowupCommands = materialFollowupCommands;
	}

	public Set<DocumentDTO> getDocumentDTOs() {
		return documentDTOs;
	}

	public void setDocumentDTOs(Set<DocumentDTO> documentDTOs) {
		this.documentDTOs = documentDTOs;
	}

	public Set<OrderHardwareCommand> getOrderHardwareCommands() {
		return orderHardwareCommands;
	}

	public void setOrderHardwareCommands(Set<OrderHardwareCommand> orderHardwareCommands) {
		this.orderHardwareCommands = orderHardwareCommands;
	}

	public Set<OrderShellCommand> getOrderShellCommands() {
		return orderShellCommands;
	}

	public void setOrderShellCommands(Set<OrderShellCommand> orderShellCommands) {
		this.orderShellCommands = orderShellCommands;
	}

	public Set<OrderPackingCommand> getOrderPackingCommands() {
		return orderPackingCommands;
	}

	public void setOrderPackingCommands(Set<OrderPackingCommand> orderPackingCommands) {
		this.orderPackingCommands = orderPackingCommands;
	}

	public Set<OrderFittingCommand> getOrderFittingCommands() {
		return orderFittingCommands;
	}

	public void setOrderFittingCommands(Set<OrderFittingCommand> orderFittingCommands) {
		this.orderFittingCommands = orderFittingCommands;
	}

	public OrderOSCommand getOrderOSCommand() {
		return orderOSCommand;
	}

	public void setOrderOSCommand(OrderOSCommand orderOSCommand) {
		this.orderOSCommand = orderOSCommand;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Description(value="客户名称")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Description(value="客户编码")
	public String getClientNameCode() {
		return clientNameCode;
	}

	public void setClientNameCode(String clientNameCode) {
		this.clientNameCode = clientNameCode;
	}

	@Description(value="客户交期")
	public Date getDateDelivery() {
		return dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	@Description(value="验货日期")
	public Date getDateExamine() {
		return dateExamine;
	}

	public void setDateExamine(Date dateExamine) {
		this.dateExamine = dateExamine;
	}

	@Description(value="下单日期")
	public Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	@Description(value="备注说明")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(value="所在国家")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Description(value="订单编号")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Description(value="订单备品")
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

	@Description(value="付款方式")
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Description(value="交货地点")
	public String getPlaceDelivery() {
		return placeDelivery;
	}

	public void setPlaceDelivery(String placeDelivery) {
		this.placeDelivery = placeDelivery;
	}

	@Description(value="客户机型")
	public String getProductClient() {
		return productClient;
	}

	public void setProductClient(String productClient) {
		this.productClient = productClient;
	}

	@Description(value="工厂机型")
	public String getProductFactory() {
		return productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	@Description(value="订单数量")
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

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	

	@Override
	public String toString() {
		final int maxLen = 10;
		return "OrderInfoCommand [id=" + id + ", clientName=" + clientName + ", clientNameCode=" + clientNameCode
				+ ", dateDelivery=" + dateDelivery + ", dateExamine=" + dateExamine + ", dateOrder=" + dateOrder + ", description=" + description + ", district=" + district
				+ ", orderNo=" + orderNo + ", orderSpare=" + orderSpare + ", pid=" + pid + ", payment=" + payment + ", placeDelivery=" + placeDelivery + ", productClient="
				+ productClient + ", productFactory=" + productFactory + ", quantity=" + quantity + ", state=" + state + ", updatetime=" + updatetime + ", sellerId=" + sellerId
				+ ", seller=" + seller + ", merchandiserId=" + merchandiserId + ", merchandiser=" + merchandiser + ", orderOSCommand=" + orderOSCommand
				+ ", orderHardwareCommands=" + (orderHardwareCommands != null ? toString(orderHardwareCommands, maxLen) : null) + ", orderShellCommands="
				+ (orderShellCommands != null ? toString(orderShellCommands, maxLen) : null) + ", orderPackingCommands="
				+ (orderPackingCommands != null ? toString(orderPackingCommands, maxLen) : null) + ", orderFittingCommands="
				+ (orderFittingCommands != null ? toString(orderFittingCommands, maxLen) : null) + ", materialFollowupCommands="
				+ (materialFollowupCommands != null ? toString(materialFollowupCommands, maxLen) : null) + ", documentDTOs="
				+ (documentDTOs != null ? toString(documentDTOs, maxLen) : null) + "]";
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

}
