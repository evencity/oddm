package com.apical.oddm.application.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.material.ProductDaoI;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.page.Pager;

@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductServiceI {

	@Autowired
	private ProductDaoI productDao;
	
	@Override
	public Pager<Product> dataGrid() {
		return productDao.dataGrid();
	}

	@Override
	public Pager<Product> dataGrid(String productTypeName) {
		return productDao.dataGrid(productTypeName);
	}

	@Override
	public Product getProductByName(String productName) {
		return productDao.getProductByName(productName);
	}

	@Override
	public List<Product> listProduct(String productTypeName) {
		return productDao.listProduct(productTypeName);
	}

	@Override
	public Pager<Product> dataGrid(Product product) {
		return productDao.dataGrid(product);
	}

	@Override
	public Product isExistProduct(String productName) {
		return productDao.isExistProduct(productName);
	}

	@Override
	public Product getProductTypeByName(String productName) {
		return productDao.getProductTypeByName(productName);
	}
}
