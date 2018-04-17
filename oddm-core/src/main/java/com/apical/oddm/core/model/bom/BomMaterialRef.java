package com.apical.oddm.core.model.bom;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * The persistent class for the bom_material_ref database table.
 * 
 */
@SuppressWarnings("deprecation")
@Entity
@Table(name="bom_material_ref")
@NamedQuery(name="BomMaterialRef.findAll", query="SELECT b FROM BomMaterialRef b")
public class BomMaterialRef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String brand;

	@OneToOne(fetch = FetchType.LAZY)
	@Cascade(value = {CascadeType.MERGE, CascadeType.DELETE, CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN}) //会级联增删改
	@JoinColumn(name="contacts")
	private BomMaterialContact contacts;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="contacts", updatable=false, insertable=false)
	private Integer contact;
	
	private String description;

	@Column(name="material_code")
	private String materialCode;

	private Integer seq;

	@Column(name="product_name")
	private String productName;

	private String specification;

	private Integer type;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	@Column(name="usage_amount1")
	private Integer usageAmount1 = 0;

	@Column(name="usage_amount2")
	private Integer usageAmount2 = 1;

	@Transient
	private String usageAmount;
	
	//bi-directional many-to-one association to BomInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bom_infoid")
	private BomInfo bomInfo;

	/**
	 * 只供获取，不能set
	 */
	@Column(name="bom_infoid", updatable=false, insertable=false)
	private Integer bomInfoId;

	public Integer getBomInfoId() {
		return bomInfoId;
	}

	public void setBomInfoId(Integer bomInfoId) {
		this.bomInfoId = bomInfoId;
	}

	public BomMaterialRef() {
	}

	public BomMaterialRef(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContact() {
		return contact;
	}

	/**
	 * 只供获取，不能set
	 * @param contact
	 */
	public void setContact(Integer contact) {
		this.contact = contact;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BomMaterialContact getContacts() {
		return this.contacts;
	}

	public void setContacts(BomMaterialContact contacts) {
		this.contacts = contacts;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getUsageAmount1() {
		return this.usageAmount1;
	}

	public void setUsageAmount1(Integer usageAmount1) {
		this.usageAmount1 = usageAmount1;
	}

	public Integer getUsageAmount2() {
		return this.usageAmount2;
	}

	public void setUsageAmount2(Integer usageAmount2) {
		this.usageAmount2 = usageAmount2;
	}

	public BomInfo getBomInfo() {
		return this.bomInfo;
	}

	public void setBomInfo(BomInfo bomInfo) {
		this.bomInfo = bomInfo;
	}

	public String getUsageAmount() {
		if (StringUtils.isBlank(usageAmount)) {
			if(usageAmount1 != null && usageAmount1 != 0) {
				if (usageAmount2 == null || usageAmount2 <= 1) {
					return usageAmount1+"";
				} else {
					return usageAmount1+"/"+usageAmount2;
				}
			} else {
				return "0";
			}
		}
		return usageAmount;
	}

	public void setUsageAmount(String usageAmount) {
		if (StringUtils.isNotBlank(usageAmount)) {
			//System.err.println("usageAmount "+usageAmount);
			String[] split = usageAmount.split("/");
			//try {
			if (split.length > 1) {
				usageAmount1 = Integer.parseInt(split[0]);
				usageAmount2 = Integer.parseInt(split[1]);
			} else if (split.length == 1){
				usageAmount1 = Integer.parseInt(split[0]);
			}
			/*} catch (NumberFormatException e) {
				usageAmount1 = 0;
				usageAmount2 = 1;
			}*/
		}
		this.usageAmount = usageAmount;
	}

	public static void main(String[] args) {
		String ss = "11/22";
		String[] split = ss.split("/");
		System.out.println(split[0]);
	}
}