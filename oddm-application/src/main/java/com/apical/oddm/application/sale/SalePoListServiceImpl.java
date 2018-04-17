package com.apical.oddm.application.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sale.SalePoListDaoI;
import com.apical.oddm.core.model.sale.SalePoList;

@Service("salePoListService")
public class SalePoListServiceImpl extends BaseServiceImpl<SalePoList> implements SalePoListServiceI {
	
	@Autowired
	private SalePoListDaoI salePoListDaoI;
	
	@Override
	public List<SalePoList> getByPoId(int poId) {
		return salePoListDaoI.getByPoId(poId);
	}

}
