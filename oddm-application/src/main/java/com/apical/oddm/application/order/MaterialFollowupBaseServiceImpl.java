package com.apical.oddm.application.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.order.MaterialFollowupBaseDaoI;
import com.apical.oddm.core.model.order.MaterialFollowupBase;

@Service("materialFollowupBaseService")
public class MaterialFollowupBaseServiceImpl extends BaseServiceImpl<MaterialFollowupBase> implements MaterialFollowupBaseServiceI {

	@Autowired
	private MaterialFollowupBaseDaoI materialFollowupBaseDao;
	
	@Override
	public MaterialFollowupBase get() {
		return materialFollowupBaseDao.get();
	}

}
