package com.apical.oddm.application.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sale.SalePoAlterDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePoAlt;

@Service("salePoAlterService")
public class SalePoAlterServiceImpl extends BaseServiceImpl<SalePoAlt> implements SalePoAlterServiceI {

	@Autowired
	private SalePoAlterDaoI salePoAlterDao;

	@Override
	public Pager<SalePoAlt> dataGrid(int mftId) {
		return salePoAlterDao.dataGrid(mftId);
	}

}
