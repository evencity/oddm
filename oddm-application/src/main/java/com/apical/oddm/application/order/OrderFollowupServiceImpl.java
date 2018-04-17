package com.apical.oddm.application.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.OrderFollowupDaoI;
import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;

@Service("orderFollowupService")
public class OrderFollowupServiceImpl extends BaseServiceImpl<OrderFollowup> implements OrderFollowupServiceI {

	@Autowired
	private OrderFollowupDaoI orderFollowupDao;
	
	/*@Override
	public Pager<OrderFollowup> dataGrid() {
		return orderFollowupDao.dataGrid();
	}*/

	/*@Override
	public Pager<OrderFollowup> dataGrid(Set<Integer> states) {
		return orderFollowupDao.dataGrid(states);
	}*/

	@Override
	public Pager<OrderFollowup> dataGridByOrderInfo(OrderInfo orderInfo, Set<Integer> states) {
		return orderFollowupDao.dataGridByOrder(orderInfo, states);
	}

	@Override
	public Pager<OrderFollowup> dataGridByOrderNo(String orderNo) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderNo(orderNo);
		return orderFollowupDao.dataGridByOrder(orderInfo, null);
	}
	 /*
	@Override
	public Pager<OrderFollowup> dataGridByClientName(String clientName) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setClientName(clientName);
		return orderFollowupDao.dataGridByOrder(orderInfo);
	}*/

/*	@Override
	public Pager<OrderFollowup> dataGridByOrderFollowup(OrderFollowup orderFollowup) {
		return orderFollowupDao.dataGridByOrderFollowup(orderFollowup);
	}*/

	@Override
	public Pager<OrderFollowup> dataGridByDateClient(Date startDate,
			Date endDate) {
		return orderFollowupDao.dataGridByDateClient(startDate, endDate);
	}

	@Override
	public Pager<OrderFollowup> dataGridByDateOrder(Date startDate, Date endDate) {
		return orderFollowupDao.dataGridByDateOrder(startDate, endDate);
	}

	@Override
	public OrderFollowup getByOrderInfo(OrderInfo orderInfo) {
		return orderFollowupDao.getByOrderInfo(orderInfo);
	}

	@Override
	public List<OrderFollowup> listAll(OrderInfo orderInfo, Set<Integer> states) {
		return orderFollowupDao.listAll(orderInfo, states);
	}

	@Override
	public void updateUpdateTime(int id) {
		orderFollowupDao.updateUpdateTime(id);
	}

}
