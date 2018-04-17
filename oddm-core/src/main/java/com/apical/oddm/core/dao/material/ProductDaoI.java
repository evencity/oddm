package com.apical.oddm.core.dao.material;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.page.Pager;

/**
 * 机型表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ProductDaoI extends BaseDaoI<Product> {

	/**
	 * 分页获取机型列表
	 * @return
	 */
	public Pager<Product> dataGrid();

	/**
	 * 通过产品类型名称查询机型列表
	 * @param productTypeName
	 * @return
	 */
	public Pager<Product> dataGrid(String productTypeName);

	/**
	 * 接收下面参数
	 * @param product
	 * @return
	 */
	public Pager<Product> dataGrid(Product product);
	
	/**
	 * 通过产品名称获取机型
	 * @param productName 产品名称
	 * @return 对象全部信息
	 */
	public Product getProductByName(String productName);

	/**
	 * @param productName
	 * @return 仅返回机型和产品类型
	 */
	public Product getProductTypeByName(String productName);
	
	/**
	 * 通过产品名称查询前10个，半模糊查询，如果为空，则默认查询
	 * @param productTypeName
	 * @return
	 */
	public List<Product> listProduct(String productTypeName);
	
	/**
	 * 判断机型是否存在
	 * @param productName
	 * @return 慢加载
	 */
	public Product isExistProduct(String productName);
}
