package com.apical.oddm.application.produce;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.produce.OrderRecordDaoI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderRecord;

@Service("orderRecordService")
public class OrderRecordServiceImpl extends BaseServiceImpl<OrderRecord	> implements OrderRecordServiceI {

	@Autowired
	private OrderRecordDaoI orderRecordDao;
	
	@Override
	public Pager<OrderRecord> dataGrid() {
		return orderRecordDao.dataGrid();
	}

	@Override
	public Pager<OrderRecord> dataGrid(OrderInfo orderInfo) {
		return orderRecordDao.dataGrid(orderInfo);
	}

	@Override
	public Pager<OrderRecord> dataGridByDateUpdate(Date startDate, Date endDate) {
		return orderRecordDao.dataGridByDateUpdate(startDate, endDate);
	}

	@Override
	public Pager<OrderRecord> dataGridByTypeId(int typeId) {
		return orderRecordDao.dataGridByTypeId(typeId);
	}

	@Override
	public Pager<OrderRecord> dataGridByTypeName(int typeName) {
		return orderRecordDao.dataGridByTypeName(typeName);
	}

}
