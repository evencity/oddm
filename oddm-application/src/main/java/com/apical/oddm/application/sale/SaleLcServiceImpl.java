package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sale.SaleLcDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SaleLc;

@Service("saleLcService")
public class SaleLcServiceImpl extends BaseServiceImpl<SaleLc> implements SaleLcServiceI {

	@Autowired
	private SaleLcDaoI saleLcDao;

	@Override
	public Pager<SaleLc> dataGrid() {
		return saleLcDao.dataGrid();
	}

	@Override
	public Pager<SaleLc> dataGrid(SaleLc saleLc) {
		return saleLcDao.dataGrid(saleLc);
	}

	@Override
	public Pager<SaleLc> dataGridByDateOrder(Date startDate, Date endDate) {
		return saleLcDao.dataGridByDateOrder(startDate, endDate);
	}

	@Override
	public List<SaleLc> list(SaleLc saleLc) {
		return saleLcDao.list(saleLc);
	}
	
}
