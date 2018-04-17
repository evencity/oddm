package com.apical.oddm.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.OrderHardwareDaoI;
import com.apical.oddm.core.model.order.OrderHardware;

@Service("orderHardwareService")
public class OrderHardwareServiceImpl extends BaseServiceImpl<OrderHardware> implements OrderHardwareServiceI {

	@Autowired
	private OrderHardwareDaoI orderHardwareDao;
	
	@Override
	public List<OrderHardware> dataGrid(int orderId) {
		return orderHardwareDao.dataGrid(orderId);
	}

}
