package com.apical.oddm.core.model.material;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="product")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	private Integer state;

	@Column(insertable = false, updatable = false)
	private Timestamp updatetime;

	@Column(insertable = false, updatable = false)
	private Timestamp timestamp;
	
	//bi-directional many-to-one association to ProductType
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type")
	private ProductType productType;

	@ManyToMany(fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE) //当hibernate查找的时候,table2里的数据没有与table1相匹配的,这样就会报No row with the given identifier exists
	@JoinTable(name = "product_bare", joinColumns = { @JoinColumn(name = "product_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "bare_id", nullable = false, updatable = false) })
	@OrderBy("id ASC")
	private Set<MaterialBare> materialBares = new HashSet<MaterialBare>(0);

/*	@ManyToMany(fetch = FetchType.LAZY)
  	@NotFound(action=NotFoundAction.IGNORE)
	@JoinTable(name = "product_packing", joinColumns = { @JoinColumn(name = "product_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "packing_id", nullable = false, updatable = false) })
	@OrderBy("id ASC")*/
	@Transient
	private Set<MaterialPacking> materialPackings = new HashSet<MaterialPacking>(0);

	public Product() {
	}

	public Product(Integer id) {
		this.id = id;
	}
	
	public Set<MaterialBare> getMaterialBares() {
		return materialBares;
	}

	public void setMaterialBares(Set<MaterialBare> materialBares) {
		this.materialBares = materialBares;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Set<MaterialPacking> getMaterialPackings() {
		return materialPackings;
	}

	/*	public void setMaterialPackings(Set<MaterialPacking> materialPackings) {
		this.materialPackings = materialPackings;
	}
*/
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public ProductType getProductType() {
		return this.productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

}