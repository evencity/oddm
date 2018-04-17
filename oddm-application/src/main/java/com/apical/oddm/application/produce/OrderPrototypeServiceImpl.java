package com.apical.oddm.application.produce;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.produce.OrderPrototypeDaoI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderPrototype;

@Service("orderPrototypeService")
public class OrderPrototypeServiceImpl extends BaseServiceImpl<OrderPrototype> implements OrderPrototypeServiceI {

	@Autowired
	private OrderPrototypeDaoI orderPrototypeDao;
	
	@Override
	public Pager<OrderPrototype> dataGrid() {
		return orderPrototypeDao.dataGrid();
	}

	@Override
	public Pager<OrderPrototype> dataGrid(OrderInfo orderInfo) {
		return orderPrototypeDao.dataGrid(orderInfo);
	}

	@Override
	public Pager<OrderPrototype> dataGridByDateUpdate(Date startDate,
			Date endDate) {
		return orderPrototypeDao.dataGridByDateUpdate(startDate, endDate);
	}

	@Override
	public Pager<OrderPrototype> dataGrid(Set<Integer> states) {
		return orderPrototypeDao.dataGrid(states);
	}

}
