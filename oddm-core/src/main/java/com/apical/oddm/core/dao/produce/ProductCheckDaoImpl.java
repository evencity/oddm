package com.apical.oddm.core.dao.produce;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.ProductCheck;

@Repository("productCheckDao")
@Deprecated
public class ProductCheckDaoImpl extends BaseDaoImpl<ProductCheck> implements ProductCheckDaoI {

	@Override
	public Pager<ProductCheck> dataGrid() {
		String hql = "from ProductCheck t join fetch t.productType";
		return this.find(hql);
	}

	@Override
	public Pager<ProductCheck> getProductCheckByType(String productTypeName) {
		String hql = "select t from ProductCheck t join fetch t.productType p where p.name=?";
		return this.find(hql, productTypeName);
	}

	@Override
	public List<ProductCheck> getProductCheckByProductName(String productName) {
		String hql = "select t from ProductCheck t join t.productType p join p.products q where q.name=?";
		return this.list(hql, productName);
	}
}
