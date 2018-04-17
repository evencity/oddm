package com.apical.oddm.application.sys;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.SysNotice;


/**
 * 通知公告表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface SysNoticeServiceI extends BaseServiceI<SysNotice> {

	/**
	 * 分页获取所有列表
	 * @param title
	 * @param states 状态
	 * @return
	 */
	public Pager<SysNotice> dataGrid(SysNotice sysNotice, Set<Integer> states);
	
	/**
	 * 分页获取列表
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public Pager<SysNotice> dataGrid(Date startTime, Date endTime);

	/**
	 * 通过公告表查出公告
	 * @param title
	 * @return
	 */
	@Deprecated
	public Pager<SysNotice> dataGrid(String title);
}
