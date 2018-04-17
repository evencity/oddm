package com.apical.oddm.application.material;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.page.Pager;


/**
 * 机型表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ProductServiceI extends BaseServiceI<Product> {

	/**
	 * 分页获取机型列表
	 * @return
	 */
	public Pager<Product> dataGrid();
	
	/**
	 * 通过产品类型名称分页获取机型列表
	 * @param productTypeName 产品类型名称
	 * @return
	 */
	@Deprecated
	public Pager<Product> dataGrid(String productTypeName);
	
	/**
	 * 接收下面参数
	 * @param product.setName() 机型名称
	 * @param product.setProductType(new ProductType(1)) 机型类型名称对应的id
	 * @return
	 */
	public Pager<Product> dataGrid(Product product);

	/**
	 * 通过产品名称查询前10个，半模糊查询，如果为空，则默认查询
	 * @param productTypeName
	 * @return
	 */
	public List<Product> listProduct(String productTypeName);

	/**
	 * 通过产品名称获取机型
	 * @param productName 产品名称
	 * @return 对象全部信息，包括物料
	 */
	public Product getProductByName(String productName);
	
	/**
	 * @param productName
	 * @return 仅返回机型和产品类型
	 */
	public Product getProductTypeByName(String productName);
	
	/**
	 * 判断机型是否存在
	 * @param productName
	 * @return 非空则存在，慢加载
	 */
	public Product isExistProduct(String productName);
}
