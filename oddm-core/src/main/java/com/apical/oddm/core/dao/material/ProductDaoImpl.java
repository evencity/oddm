package com.apical.oddm.core.dao.material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.page.Pager;

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDaoI {

	@Override
	public Pager<Product> dataGrid() {
		String hql = "from Product t join fetch t.productType p";
		return this.find(hql);
	}

	@Override
	public Product get(int id) {
		String hql = "from Product t left join fetch t.productType o left join fetch t.materialBares b left join fetch b.materialType where t.id=?";
		return (Product) this.queryObject(hql, id);
	}

	@Override
	public Pager<Product> dataGrid(String productTypeName) {
		String hql = "select t from Product t join fetch t.productType p where p.name=?";
		return this.find(hql, productTypeName);
	}

	@Override
	public Product getProductByName(String productName) {
		String hql = "select t from Product t left join fetch t.materialBares left join fetch t.productType where t.name=?";
		return (Product) this.queryObject(hql, productName);
	}

	@Override
	public List<Product> listProduct(String productTypeName) {
		if (StringUtils.isNotBlank(productTypeName)) {
			String hql = "from Product t where t.name like ? order by t.id desc";
			return this.listPage(hql, "%"+productTypeName+"%", null);
		} else {
			String hql = "from Product t order by t.id desc";
			return this.listPage(hql, null, null);
		}
	}

	@Override
	public Pager<Product> dataGrid(Product product) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from Product t left join fetch t.productType p where 1=1";
		if (product != null) {
			if (product.getProductType() != null) {
				if (product.getProductType().getId() != null) {
					hql +=" and p.id=:id";
					params.put("id", product.getProductType().getId());
				}
			}
			if (StringUtils.isNotBlank(product.getName())) {
				hql +=" and t.name like :tname";
				params.put("tname", "%"+product.getName()+"%");
			}
		}
		hql += " order by t.updatetime desc";
		return this.findByAlias(hql, params);
	}

	@Override
	public Product isExistProduct(String productName) {
		String hql = "select t from Product t where t.name=?";
		return (Product) this.queryObject(hql, productName);
	}

	@Override
	public Product getProductTypeByName(String productName) {
		String hql = "select t from Product t left join fetch t.productType where t.name=?";
		return (Product) this.queryObject(hql, productName);
	}

}
