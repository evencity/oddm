package com.apical.oddm.facade.material.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.material.ProductTypeServiceI;
import com.apical.oddm.core.model.material.ProductType;
import com.apical.oddm.facade.material.ProductTypeFacade;
import com.apical.oddm.facade.material.command.ProductTypeCommand;
import com.apical.oddm.facade.material.dto.ProductTypeDTO;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:52:57
 * @version 1.0
 */
@Component("productTypeFacade")
public class ProductTypeFacadeImpl implements ProductTypeFacade {

	@Autowired
	private ProductTypeServiceI productTypeService;

	@Override
	public List<ProductTypeDTO> pageList() {
		// TODO Auto-generated method stub
		List<ProductTypeDTO> list = new ArrayList<ProductTypeDTO>();
		List<ProductType> dataGrid = productTypeService.dataGrid();
		for (ProductType productType : dataGrid) {
			ProductTypeDTO productTypeDTO = new ProductTypeDTO();
			BeanUtils.copyProperties(productType, productTypeDTO);
			list.add(productTypeDTO);
		}
		return list;
	}

	@Override
	public Boolean add(ProductTypeCommand productTypeCommand) {
		// TODO Auto-generated method stub
		ProductType productType = new ProductType();
		BeanUtils.copyProperties(productTypeCommand, productType);
		Serializable id = productTypeService.add(productType);
		if (id != null) {
			return true;
		}
		return false;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		productTypeService.delete(id);
	}

	@Override
	public void edit(ProductTypeCommand productTypeCommand) {
		// TODO Auto-generated method stub
		ProductType productType = new ProductType();
		BeanUtils.copyProperties(productTypeCommand, productType);
		productTypeService.edit(productType);
	}

	@Override
	public ProductTypeDTO get(Integer id) {
		// TODO Auto-generated method stub
		ProductTypeDTO productTypeDTO = new ProductTypeDTO();
		ProductType productType = productTypeService.get(id);
		BeanUtils.copyProperties(productType, productTypeDTO);
		return productTypeDTO;
	}
}
