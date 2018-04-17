package com.apical.oddm.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.OrderFollowupAlterDaoI;
import com.apical.oddm.core.model.order.OrderFollowupAlter;
import com.apical.oddm.core.model.page.Pager;

@Service("orderFollowupAlterService")
public class OrderFollowupAlterServiceImpl extends BaseServiceImpl<OrderFollowupAlter> implements OrderFollowupAlterServiceI {

	@Autowired
	private OrderFollowupAlterDaoI orderFollowupAlterDao;
	
	@Override
	public List<OrderFollowupAlter> listGrid(int followUpId) {
		return orderFollowupAlterDao.listGrid(followUpId);
	}

	@Override
	public Pager<OrderFollowupAlter> dataGrid(int followUpId) {
		return orderFollowupAlterDao.dataGrid(followUpId);
	}

}
