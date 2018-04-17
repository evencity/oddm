package com.apical.oddm.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.MaterialFollowupAlterDaoI;
import com.apical.oddm.core.model.order.MaterialFollowupAlter;
import com.apical.oddm.core.model.page.Pager;

@Service("materialFollowupAlterService")
public class MaterialFollowupAlterServiceImpl extends BaseServiceImpl<MaterialFollowupAlter> implements MaterialFollowupAlterServiceI {

	@Autowired
	private MaterialFollowupAlterDaoI materialFollowupAlterDao;
	
	@Override
	public List<MaterialFollowupAlter> listGrid(int followUpId) {
		return materialFollowupAlterDao.listGrid(followUpId);
	}

	@Override
	public Pager<MaterialFollowupAlter> dataGrid(int followUpId) {
		return materialFollowupAlterDao.dataGrid(followUpId);
	}

}
