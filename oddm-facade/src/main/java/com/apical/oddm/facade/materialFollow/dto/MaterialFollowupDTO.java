package com.apical.oddm.facade.materialFollow.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.apical.oddm.facade.order.dto.OrderInfoDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月9日 下午2:46:14 
 * @version 1.0 
 */

public class MaterialFollowupDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String dateCommit;

	private String dateDeliver;

	private String dateFinish;

	private String dateOnline;

	private String dateSubmit;

	private Integer dateTo;

	private String merchandiser;

	private String mtCode;

	private String mtCondition;

	private String prodLine;

	private Integer quality;

	//private String section;

	private String specification;

	private Integer state;
	
	private String timestamp;
	
	private OrderInfoDTO orderInfoDTO;

	private Set<MaterialFollowupAlterDTO> materialFollowupAlterDTOs;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDateCommit() {
		return dateCommit;
	}

	public void setDateCommit(String dateCommit) {
		this.dateCommit = dateCommit;
	}

	public String getDateDeliver() {
		return dateDeliver;
	}

	public void setDateDeliver(String dateDeliver) {
		this.dateDeliver = dateDeliver;
	}

	public String getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(String dateFinish) {
		this.dateFinish = dateFinish;
	}

	public String getDateOnline() {
		return dateOnline;
	}

	public void setDateOnline(String dateOnline) {
		this.dateOnline = dateOnline;
	}

	public String getDateSubmit() {
		return dateSubmit;
	}

	public void setDateSubmit(String dateSubmit) {
		this.dateSubmit = dateSubmit;
	}

	public Integer getDateTo() {
		return dateTo;
	}

	public void setDateTo(Integer dateTo) {
		this.dateTo = dateTo;
	}

	public String getMerchandiser() {
		return merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	public String getMtCode() {
		return mtCode;
	}

	public void setMtCode(String mtCode) {
		this.mtCode = mtCode;
	}

	public String getMtCondition() {
		return mtCondition;
	}

	public void setMtCondition(String mtCondition) {
		this.mtCondition = mtCondition;
	}

	public String getProdLine() {
		return prodLine;
	}

	public void setProdLine(String prodLine) {
		this.prodLine = prodLine;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	/*public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}*/

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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public OrderInfoDTO getOrderInfoDTO() {
		return orderInfoDTO;
	}

	public void setOrderInfoDTO(OrderInfoDTO orderInfoDTO) {
		this.orderInfoDTO = orderInfoDTO;
	}

	public Set<MaterialFollowupAlterDTO> getMaterialFollowupAlterDTOs() {
		return materialFollowupAlterDTOs;
	}

	public void setMaterialFollowupAlterDTOs(Set<MaterialFollowupAlterDTO> materialFollowupAlterDTOs) {
		this.materialFollowupAlterDTOs = materialFollowupAlterDTOs;
	}

	
}
