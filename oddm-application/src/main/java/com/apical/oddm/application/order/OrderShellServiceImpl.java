package com.apical.oddm.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.OrderShellDaoI;
import com.apical.oddm.core.model.order.OrderShell;

@Service("orderShellService")
public class OrderShellServiceImpl extends BaseServiceImpl<OrderShell> implements OrderShellServiceI {

	@Autowired
	private OrderShellDaoI orderShellDao;
	
	@Override
	public List<OrderShell> dataGrid(int orderId) {
		return orderShellDao.dataGrid(orderId);
	}

}
