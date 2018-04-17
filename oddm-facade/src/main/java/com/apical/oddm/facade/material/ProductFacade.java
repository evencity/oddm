package com.apical.oddm.facade.material;

import java.util.List;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.command.ProductCommand;
import com.apical.oddm.facade.material.dto.ProductDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:52:24 
 * @version 1.0 
 */

public interface ProductFacade {

	/**
	 * 分页获取机型列表
	 * @return
	 */
	public BasePage<ProductDTO> pageList(ProductCommand productCommand,PageCommand pageCommand);
	

	/**
	 * 通过产品名称获取机型
	 * @param productName 产品名称
	 * @return 对象全部信息，包括物料
	 */
	public ProductDTO getProductByName(String productName);
	
	/**
	 * 增加产品
	 * @param ProductDTO
	 * @return
	 */
	public Boolean add(ProductCommand productCommand);
	
	/**
	 * 通过产品id删除物料类型
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 修改产品
	 * @param materialType
	 */
	public void edit(ProductCommand productCommand);

	/**
	 * 获取单个产品
	 * @param id
	 * @return
	 */
	public ProductDTO get(Integer id);
	
	/**
	 * 通过产品名称查询前10个，半模糊查询，如果为空，则默认查询
	 * @param productTypeName
	 * @return
	 */
	public List<ProductDTO> listProduct(String productTypeName);
	
	/**
	 * 接收下面参数
	 * @param product.setName() 机型名称
	 * @param product.setProductType(new ProductType("PND")) 机型类型名称
	 * @return
	 */
	public BasePage<ProductDTO> dataGrid(ProductCommand productCommand,PageCommand pageCommand);
	/**
	 * 判断机型是否存在
	 * @param productName
	 * @return 非空则存在，慢加载
	 */
	public Boolean isExistProduct(String productName);
	
	/**
	 * 冻结产品
	 * @param materialType
	 */
	public Boolean freeze(ProductCommand productCommand);
	
}
