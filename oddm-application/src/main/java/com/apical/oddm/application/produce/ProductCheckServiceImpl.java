package com.apical.oddm.application.produce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.produce.ProductCheckDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.ProductCheck;

@Deprecated
@Service("productCheckService")
public class ProductCheckServiceImpl extends BaseServiceImpl<ProductCheck> implements ProductCheckServiceI {

	@Autowired
	private ProductCheckDaoI productCheckDao;
	
	@Override
	public Pager<ProductCheck> dataGrid() {
		return productCheckDao.dataGrid();
	}

	@Override
	public Pager<ProductCheck> getProductCheckByType(String productTypeName) {
		return productCheckDao.getProductCheckByType(productTypeName);
	}

	@Override
	public List<ProductCheck> getProductCheckByProductName(String productName) {
		return productCheckDao.getProductCheckByProductName(productName);
	}

}
