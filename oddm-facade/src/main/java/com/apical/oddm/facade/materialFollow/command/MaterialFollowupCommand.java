package com.apical.oddm.facade.materialFollow.command;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.springframework.context.annotation.Description;

import com.apical.oddm.core.model.order.MaterialFollowupAlter;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月9日 下午2:46:14 
 * @version 1.0 
 */

public class MaterialFollowupCommand implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date dateCommit;

	private Date dateDeliver;

	private Date dateFinish;

	private Date dateOnline;

	private Date dateSubmit;

	private Date dateTo;

	private String merchandiser;

	private String mtCode;

	private String mtCondition;

	private String prodLine;

	private Integer quality;

//	private String section;

	private String specification;

	private Integer state;
	
	private Timestamp timestamp;
	
	//private OrderInfo orderInfo;
	
	private Integer orderId;

	private Set<MaterialFollowupAlter> materialFollowupAlters;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Description(value="料号确定日期")
	public Date getDateCommit() {
		return dateCommit;
	}

	public void setDateCommit(Date dateCommit) {
		this.dateCommit = dateCommit;
	}

	@Description(value="交货日期")
	public Date getDateDeliver() {
		return dateDeliver;
	}

	public void setDateDeliver(Date dateDeliver) {
		this.dateDeliver = dateDeliver;
	}

	@Description(value="齐料日期")
	public Date getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(Date dateFinish) {
		this.dateFinish = dateFinish;
	}

	@Description(value="组装预计上线日期")
	public Date getDateOnline() {
		return dateOnline;
	}
	
	public void setDateOnline(Date dateOnline) {
		this.dateOnline = dateOnline;
	}

	@Description(value="订单下达日期")
	public Date getDateSubmit() {
		return dateSubmit;
	}

	public void setDateSubmit(Date dateSubmit) {
		this.dateSubmit = dateSubmit;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getMerchandiser() {
		return merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	@Description(value="物料编码")
	public String getMtCode() {
		return mtCode;
	}

	public void setMtCode(String mtCode) {
		this.mtCode = mtCode;
	}

	@Description(value="整机物料齐套状况")
	public String getMtCondition() {
		return mtCondition;
	}

	public void setMtCondition(String mtCondition) {
		this.mtCondition = mtCondition;
	}

	@Description(value="生产顺位")
	public String getProdLine() {
		return prodLine;
	}

	public void setProdLine(String prodLine) {
		this.prodLine = prodLine;
	}

	@Description(value="数量")
	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	/*@Description(value="拆单")
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
*/
	@Description(value="规格型号及封装")
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

/*	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}*/

	public Set<MaterialFollowupAlter> getMaterialFollowupAlters() {
		return materialFollowupAlters;
	}

	public void setMaterialFollowupAlters(Set<MaterialFollowupAlter> materialFollowupAlters) {
		this.materialFollowupAlters = materialFollowupAlters;
	}
	
}
