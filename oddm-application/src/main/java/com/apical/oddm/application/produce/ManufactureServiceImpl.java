package com.apical.oddm.application.produce;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.produce.ManufactureDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.Manufacture;

@Service("manufactureService")
public class ManufactureServiceImpl extends BaseServiceImpl<Manufacture> implements ManufactureServiceI {

	@Autowired
	private ManufactureDaoI manufactureDao;
	
	@Override
	public Pager<Manufacture> dataGrid() {
		return manufactureDao.dataGrid();
	}

	@Override
	public Pager<Manufacture> dataGrid(Manufacture manufacture, Set<Integer> states) {
		return manufactureDao.dataGrid(manufacture, states);
	}

	@Override
	public Pager<Manufacture> dataGridByDateIssue(Date startDate, Date endDate) {
		return manufactureDao.dataGridByDateIssue(startDate, endDate);
	}

	@Override
	public Manufacture getManufacture(int id) {
		return manufactureDao.getManufacture(id);
	}

/*	@Override
	public Pager<Manufacture> dataGrid(Set<Integer> states) {
		return manufactureDao.dataGrid(states);
	}*/

	@Override
	public Pager<Manufacture> dataGridByDateUpdate(Date startDate,
			Date endDate) {
		return manufactureDao.dataGridByDateUpdate(startDate, endDate);
	}

	@Override
	public Manufacture getManufacture(Manufacture manufacture, boolean lazy) {
		return manufactureDao.getManufacture(manufacture, lazy);
	}

	@Override
	public void updateUpdateTime(Integer id) {
		manufactureDao.updateUpdateTime(id);
	}

}
