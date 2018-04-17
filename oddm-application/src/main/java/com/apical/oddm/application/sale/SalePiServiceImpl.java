package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sale.SalePiDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePi;

@Service("salePiService")
public class SalePiServiceImpl extends BaseServiceImpl<SalePi> implements SalePiServiceI {

	@Autowired
	private SalePiDaoI salePiDao;

	@Override
	public Pager<SalePi> dataGrid() {
		return salePiDao.dataGrid();
	}

	@Override
	public Pager<SalePi> dataGrid(SalePi salePi) {
		return salePiDao.dataGrid(salePi);
	}

	@Override
	public Pager<SalePi> dataGridByDateOrder(Date startDate, Date endDate) {
		return salePiDao.dataGridByDateOrder(startDate, endDate);
	}

	@Override
	public SalePi getSalePi(SalePi salePi) {
		return salePiDao.getSalePi(salePi);
	}

	@Override
	public List<SalePi> list(SalePi salePi) {
		return salePiDao.list(salePi);
	}
	
	
}
