package com.apical.oddm.application.bom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.dao.bom.BomUnreadDaoI;
import com.apical.oddm.core.model.bom.BomUnread;
import com.apical.oddm.core.model.sys.User;

@Service("bomUnreadService")
public class BomUnreadServiceImpl extends BaseServiceImpl<BomUnread> implements BomUnreadServiceI {

	@Autowired
	private BomUnreadDaoI bomUnreadDao;
	
	@Autowired
	private UserServiceI userService;
	
	@Override
	public void delete(int userId, int bomId) {
		bomUnreadDao.delete(userId, bomId);
	}

	@Override
	public void addBomUnreadBatch(int currUserId, List<User> users, int bomId) {
		List<Object> list = bomUnreadDao.listByBomId(bomId);
		if (list == null || list.isEmpty()) {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId()) {
						bomUnreadDao.add(new BomUnread(u.getId(), bomId));
					}
				}
			}
		} else {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId() && !list.contains(u.getId())) {
						bomUnreadDao.add(new BomUnread(u.getId(), bomId));
					}
				}
			}
		}
	}

	@Override
	public void deleteMonth(int month) {
		bomUnreadDao.deleteMonth(month);
	}

	@Override
	public void saveOrUpdate(BomUnread bomUnread) {
		bomUnreadDao.saveOrUpdate(bomUnread);
	}

}
