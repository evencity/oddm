package com.apical.oddm.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.OrderOSDaoI;
import com.apical.oddm.core.model.order.OrderOS;

@Service("orderOSService")
public class OrderOSServiceImpl extends BaseServiceImpl<OrderOS> implements OrderOSServiceI {

	@Autowired
	private OrderOSDaoI orderOSDao;
	
	@Override
	public OrderOS getOrderOsByGui(String gui) {
		return orderOSDao.getOrderOsByGui(gui);
	}

	@Override
	public OrderOS getOrderOsByOrderId(int orderId) {
		return orderOSDao.getOrderOsByOrderId(orderId);
	}

	@Override
	public List<Object> listTop(OrderOS orderOS) {
		return orderOSDao.listTop(orderOS);
	}

}
