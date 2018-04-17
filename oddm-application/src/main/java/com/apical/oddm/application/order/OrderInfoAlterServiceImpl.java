package com.apical.oddm.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.OrderInfoAlterDaoI;
import com.apical.oddm.core.model.order.OrderInfoAlt;
import com.apical.oddm.core.model.page.Pager;

@Service("orderInfoAlterService")
public class OrderInfoAlterServiceImpl extends BaseServiceImpl<OrderInfoAlt> implements OrderInfoAlterServiceI {

	@Autowired
	private OrderInfoAlterDaoI orderInfoAlterDao;
	
	@Override
	public List<OrderInfoAlt> listGrid(int orderId) {
		return orderInfoAlterDao.listGrid(orderId);
	}

	@Override
	public Pager<OrderInfoAlt> dataGrid(int orderId) {
		return orderInfoAlterDao.dataGrid(orderId);
	}

}
