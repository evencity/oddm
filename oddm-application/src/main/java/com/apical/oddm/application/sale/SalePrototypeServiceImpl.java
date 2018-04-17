package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sale.SalePrototypeDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePrototype;

@Service("salePrototypeService")
public class SalePrototypeServiceImpl extends BaseServiceImpl<SalePrototype> implements SalePrototypeServiceI {

	@Autowired
	private SalePrototypeDaoI salePrototypeDao;
	
	@Override
	public Pager<SalePrototype> dataGrid() {
		return salePrototypeDao.dataGrid();
	}

	@Override
	public Pager<SalePrototype> dataGrid(SalePrototype salePrototype) {
		return salePrototypeDao.dataGrid(salePrototype);
	}

	@Override
	public Pager<SalePrototype> dataGridByDateSend(Date startDate, Date endDate) {
		return salePrototypeDao.dataGridByDateSend(startDate, endDate);
	}

	@Override
	public List<SalePrototype> list(SalePrototype saleProto) {
		return salePrototypeDao.list(saleProto);
	}

}
