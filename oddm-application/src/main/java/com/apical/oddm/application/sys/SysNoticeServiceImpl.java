package com.apical.oddm.application.sys;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sys.SysNoticeDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.SysNotice;

@Service("sysNoticeService")
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNotice> implements SysNoticeServiceI {

	@Autowired
	private SysNoticeDaoI sysNoticeDao;
	
	@Override
	public Pager<SysNotice> dataGrid(SysNotice sysNotice, Set<Integer> states) {
		return sysNoticeDao.dataGrid(sysNotice, states);
	}

	@Override
	public Pager<SysNotice> dataGrid(Date startTime, Date endTime) {
		return sysNoticeDao.dataGrid(startTime, endTime);
	}

	@Override
	public Pager<SysNotice> dataGrid(String title) {
		return sysNoticeDao.dataGrid(title);
	}

}
