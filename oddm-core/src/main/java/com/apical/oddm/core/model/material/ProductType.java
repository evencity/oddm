package com.apical.oddm.core.model.material;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * The persistent class for the product_type database table.
 * 
 */
@Entity
@Table(name="product_type")
@NamedQuery(name="ProductType.findAll", query="SELECT p FROM ProductType p")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ProductType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="productType")
	private Set<Product> products = new HashSet<Product>(0);

	
/*	@OneToMany(mappedBy="productType")
	private Set<ProductCheck> ProductChecks = new HashSet<ProductCheck>(0);
	*/
	public ProductType() {
	}

	public ProductType(Integer id) {
		this.id = id;
	}

	public ProductType(String name) {
		this.name = name;
	}

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

	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setProductType(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setProductType(null);

		return product;
	}

}