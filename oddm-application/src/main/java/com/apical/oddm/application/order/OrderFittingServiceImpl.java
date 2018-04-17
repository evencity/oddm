package com.apical.oddm.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.OrderFittingDaoI;
import com.apical.oddm.core.model.order.OrderFitting;

@Service("orderFittingService")
public class OrderFittingServiceImpl extends BaseServiceImpl<OrderFitting> implements OrderFittingServiceI {

	@Autowired
	private OrderFittingDaoI orderFittingDao;
	
	@Override
	public List<OrderFitting> dataGrid(int orderId) {
		return orderFittingDao.dataGrid(orderId);
	}

}
