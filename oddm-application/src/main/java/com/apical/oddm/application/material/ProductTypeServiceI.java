package com.apical.oddm.application.material;

import java.util.List;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.material.ProductType;


/**
 * 产品类型表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ProductTypeServiceI extends BaseServiceI<ProductType> {

	/**
	 * 获取所有产品类型列表
	 * @return
	 */
	public List<ProductType> dataGrid();
	
	/**
	 * 删除产品，以及修改所关联机型的产品类型关联为空
	 * @param id
	 * @return 返回被修改的机型列表
	 */
	public Set<Product> deleteAndEdit(int id);
}
