package com.apical.oddm.application.produce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.dao.produce.ManufactureUnreadDaoI;
import com.apical.oddm.core.model.produce.ManufactureUnread;
import com.apical.oddm.core.model.sys.User;

@Service("manufactureUnreadService")
public class ManufactureUnreadServiceImpl extends BaseServiceImpl<ManufactureUnread> implements ManufactureUnreadServiceI {

	@Autowired
	private ManufactureUnreadDaoI manufactureUnreadDao;
	
	@Autowired
	private UserServiceI userService;
	
	@Override
	public void delete(int userId, int manufactureId) {
		manufactureUnreadDao.delete(userId, manufactureId);
	}

	@Override
	public void addManufactureUnreadBatch(int currUserId, List<User> users, int manufactureId) {
		List<Object> list = manufactureUnreadDao.listByManufactureId(manufactureId);
		if (list == null || list.isEmpty()) {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId()) {
						manufactureUnreadDao.add(new ManufactureUnread(u.getId(), manufactureId));
					}
				}
			}
		} else {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId() && !list.contains(u.getId())) {
						manufactureUnreadDao.add(new ManufactureUnread(u.getId(), manufactureId));
					}
				}
			}
		}
	}

	@Override
	public void deleteMonth(int month) {
		manufactureUnreadDao.deleteMonth(month);
	}

	@Override
	public void saveOrUpdate(ManufactureUnread manufactureUnread) {
		manufactureUnreadDao.saveOrUpdate(manufactureUnread);
	}

}
