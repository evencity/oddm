package com.apical.oddm.core.dao.material;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.material.ProductType;

/**
 * 产品类型表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ProductTypeDaoI extends BaseDaoI<ProductType> {

	/**
	 * 获取全部产品类型列表
	 * @return
	 */
	public List<ProductType> dataGrid();

	/**
	 * 通过产品类型名称获取产品类型
	 * @param productTypeName
	 * @return
	 */
	public ProductType getProductTypeByName(String productTypeName);
}
