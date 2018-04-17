package com.apical.oddm.application.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sale.SaleOutDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SaleOut;

@Service("saleOutServiceI")
public class SaleOutServiceImpl extends BaseServiceImpl<SaleOut> implements SaleOutServiceI {

	@Autowired
	private SaleOutDaoI saleOutDao;

	@Override
	public Pager<SaleOut> dataGrid(SaleOut saleOut) {
		return saleOutDao.dataGrid(saleOut);
	}

	@Override
	public SaleOut getDetail(int id) {
		return saleOutDao.getDetail(id); 
	}

	@Override
	public SaleOut getByOrderId(int orderId, boolean lazy) {
		return saleOutDao.getByOrderId(orderId, lazy);
	}

	@Override
	public List<SaleOut> list(SaleOut saleOut) {
		return saleOutDao.list(saleOut);
	}

/*	@Override
	public List<SaleOutYearVo> getSaleOutYearVoList(int type, String year,
			String currency) {
		return saleOutDao.getSaleOutYearVoList(type, year, currency);
	}

	@Override
	public List<SaleOutAllYearVo> getSaleOutAllYearVoList(String yearStart,
			String yearEnd, String currency) {
		return saleOutDao.getSaleOutAllYearVoList(yearStart, yearEnd, currency);
	}*/

}
