package com.apical.oddm.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.OrderPackingDaoI;
import com.apical.oddm.core.model.order.OrderPacking;

@Service("orderPackingService")
public class OrderPackingServiceImpl extends BaseServiceImpl<OrderPacking> implements OrderPackingServiceI {

	@Autowired
	private OrderPackingDaoI orderPackingDao;
	
	@Override
	public List<OrderPacking> dataGrid(int orderId) {
		return orderPackingDao.dataGrid(orderId);
	}
}
