package com.apical.oddm.facade.material;

import java.util.List;

import com.apical.oddm.facade.material.command.ProductCommand;
import com.apical.oddm.facade.material.command.ProductTypeCommand;
import com.apical.oddm.facade.material.dto.ProductDTO;
import com.apical.oddm.facade.material.dto.ProductTypeDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:52:57 
 * @version 1.0 
 */

public interface ProductTypeFacade {


	/**
	 * 获取所有产品类型列表
	 * @return
	 */
	public List<ProductTypeDTO> pageList();
	

	/**
	 * 增加产品类型
	 * @param ProductDTO
	 * @return
	 */
	public Boolean add(ProductTypeCommand productTypeCommand);
	
	/**
	 * 通过产品id删除产品类型
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 修改产品类型
	 * @param materialType
	 */
	public void edit(ProductTypeCommand productTypeCommand);

	/**
	 * 获取单个产品类型
	 * @param id
	 * @return
	 */
	public ProductTypeDTO get(Integer id);
}
