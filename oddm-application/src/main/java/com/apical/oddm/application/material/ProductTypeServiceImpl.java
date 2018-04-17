package com.apical.oddm.application.material;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.material.ProductDaoI;
import com.apical.oddm.core.dao.material.ProductTypeDaoI;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.material.ProductType;

@Service("productTypeService")
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements ProductTypeServiceI {

	@Autowired
	private ProductTypeDaoI productTypeDao;
	
	@Autowired
	private ProductDaoI productDao;
	
	@Override
	public List<ProductType> dataGrid() {
		return productTypeDao.dataGrid();
	}

	@Override
	public Set<Product> deleteAndEdit(int id) {
		ProductType productType = this.get(id);
		Set<Product> products = productType.getProducts();
		for (Product p : products) {
			p.setProductType(null); //保留机型，但是得清空外键
		}
		//this.edit(productType); //可以不用写，会自动更新
		super.delete(id);
		return products;
	}
}
