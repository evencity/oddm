package com.apical.oddm.core.dao.material;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.material.ProductType;

@Repository("productTypeDao")
public class ProductTypeDaoImpl extends BaseDaoImpl<ProductType> implements ProductTypeDaoI {

	@Override
	public List<ProductType> dataGrid() {
		return this.getNamedQuery(ProductType.class);//使用命名查询
	}

	@Override
	public ProductType getProductTypeByName(String productTypeName) {
		String hql = "from ProductType t where t.name=?";
		return (ProductType) this.queryObject(hql, productTypeName);
	}

}
