package com.apical.oddm.application.document;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.dao.document.DocumentUnreadDaoI;
import com.apical.oddm.core.model.document.DocumentUnread;
import com.apical.oddm.core.model.sys.User;

@Service("documentUnreadService")
public class DocumentUnreadServiceImpl extends BaseServiceImpl<DocumentUnread> implements DocumentUnreadServiceI {

	@Autowired
	private DocumentUnreadDaoI documentUnreadDao;
	
	@Autowired
	private UserServiceI userService;
	
	@Override
	public void delete(int userId, int docId) {
		documentUnreadDao.delete(userId, docId);
	}

	@Override
	public void addDocUnreadBatch(int currUserId, List<User> users, int docId) {
		List<Object> list = documentUnreadDao.listByDocId(docId);
		if (list == null || list.isEmpty()) {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId()) {
						documentUnreadDao.saveOrUpdate(new DocumentUnread(u.getId(), docId));
					}
				}
			}
		} else {
			if (users != null && !users.isEmpty()) {
				for (User u : users) {
					if (currUserId != u.getId() && !list.contains(u.getId())) {
						documentUnreadDao.saveOrUpdate(new DocumentUnread(u.getId(), docId));
					}
				}
			}
		}
	}

	@Override
	public void deleteMonth(int month) {
		documentUnreadDao.deleteMonth(month);
	}

}
