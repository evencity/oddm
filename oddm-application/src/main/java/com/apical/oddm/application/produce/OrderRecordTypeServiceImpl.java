package com.apical.oddm.application.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.produce.OrderRecordTypeDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderRecordType;

@Service("orderRecordTypeService")
public class OrderRecordTypeServiceImpl extends BaseServiceImpl<OrderRecordType> implements OrderRecordTypeServiceI {

	@Autowired
	private OrderRecordTypeDaoI orderRecordTypeDao;
	
	@Override
	public Pager<OrderRecordType> dataGrid() {
		return orderRecordTypeDao.dataGrid();
	}

	@Override
	public OrderRecordType getByName(String name) {
		return orderRecordTypeDao.getByName(name);
	}

}
