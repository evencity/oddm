package com.apical.oddm.application.produce;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.ProductCheck;

/**
 * 生产任务书产品检测项表操作接口
 * @author lgx
 * 2016-10-16
 */
@Deprecated
public interface ProductCheckServiceI extends BaseServiceI<ProductCheck> {

	/**
	 * 分页获取列表
	 * @return
	 */
	public Pager<ProductCheck> dataGrid();
	
	/**
	 * 通过产品类型名称查询
	 * @param productTypeName 产品类型名称，如 MVR、PND
	 * @return
	 */
	public Pager<ProductCheck> getProductCheckByType(String productTypeName);
	
	/**
	 * 通过产品名称（工厂机型）获取机型
	 * @param productName 产品名称（工厂机型）
	 * @return 产品检测项
	 */
	public List<ProductCheck> getProductCheckByProductName(String productName);
}
