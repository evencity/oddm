package com.apical.oddm.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.dao.order.OrderUnreadDaoI;
import com.apical.oddm.core.model.order.OrderUnread;
import com.apical.oddm.core.model.sys.User;

@Service("orderUnreadService")
public class OrderUnreadServiceImpl extends BaseServiceImpl<OrderUnread> implements OrderUnreadServiceI {

	@Autowired
	private OrderUnreadDaoI orderUnreadDao;
	
	@Autowired
	private UserServiceI userService;
	
	@Override
	public void delete(int userId, int orderId) {
		orderUnreadDao.delete(userId, orderId);
	}

	@Override
	public void addOrderUnreadBatch(int currUserId, List<User> users, int orderId) {
		List<Object> list = orderUnreadDao.listByOrderId(orderId);
		if (list == null || list.isEmpty()) {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId()) {
						orderUnreadDao.add(new OrderUnread(u.getId(), orderId));
					}
				}
			}
		} else {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId() && !list.contains(u.getId())) {
						orderUnreadDao.add(new OrderUnread(u.getId(), orderId));
					}
				}
			}
		}
	}

	@Override
	public void deleteMonth(int month) {
		orderUnreadDao.deleteMonth(month);
	}

	@Override
	public void saveOrUpdate(OrderUnread orderUnread) {
		orderUnreadDao.saveOrUpdate(orderUnread);
	}

}
