package com.apical.oddm.core.dao.produce;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.ProductCheck;

/**
 * 产品检测项表dao操作接口
 * @author lgx
 * 2016-10-16
 */
@Deprecated
public interface ProductCheckDaoI extends BaseDaoI<ProductCheck> {

	/**
	 * 分页获取列表
	 * @return
	 */
	public Pager<ProductCheck> dataGrid();

	/**
	 * 通过产品类型名称查询
	 * @param productTypeName
	 * @return 产品检测项
	 */
	public Pager<ProductCheck> getProductCheckByType(String productTypeName);
	
	/**
	 * 通过产品名称（工厂机型）获取机型
	 * @param productName 产品名称（工厂机型）
	 * @return 产品检测项
	 */
	public List<ProductCheck> getProductCheckByProductName(String productName);

}
