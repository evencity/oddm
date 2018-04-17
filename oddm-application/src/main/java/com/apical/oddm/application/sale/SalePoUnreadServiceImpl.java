package com.apical.oddm.application.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.dao.sale.SalePoUnreadDaoI;
import com.apical.oddm.core.model.sale.SalePoUnread;
import com.apical.oddm.core.model.sys.User;

@Service("salePoUnreadService")
public class SalePoUnreadServiceImpl extends BaseServiceImpl<SalePoUnread> implements SalePoUnreadServiceI {

	@Autowired
	private SalePoUnreadDaoI salePoUnreadDao;
	
	@Autowired
	private UserServiceI userService;
	
	@Override
	public void delete(int userId, int poId) {
		salePoUnreadDao.delete(userId, poId);
	}

	@Override
	public void addSalePoUnreadBatch(int currUserId, List<User> users, int poId) {
		List<Object> list = salePoUnreadDao.listByPoId(poId);
		if (list == null || list.isEmpty()) {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId()) {
						salePoUnreadDao.add(new SalePoUnread(u.getId(), poId));
					}
				}
			}
		} else {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId() && !list.contains(u.getId())) {
						salePoUnreadDao.add(new SalePoUnread(u.getId(), poId));
					}
				}
			}
		}
	}

	@Override
	public void deleteMonth(int month) {
		salePoUnreadDao.deleteMonth(month);
	}

	@Override
	public void saveOrUpdate(SalePoUnread orderUnread) {
		salePoUnreadDao.saveOrUpdate(orderUnread);
	}

}
